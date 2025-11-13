package fi.ishtech.practice.oms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import fi.ishtech.practice.oms.entity.Product;
import fi.ishtech.practice.oms.entity.ProductDocument;
import fi.ishtech.practice.oms.mapper.ProductMapper;
import fi.ishtech.practice.oms.payload.ProductVo;
import fi.ishtech.practice.oms.payload.filter.ProductFilterParams;
import fi.ishtech.practice.oms.repo.ProductDocumentRepo;
import fi.ishtech.practice.oms.repo.ProductRepo;
import fi.ishtech.practice.oms.service.ProductService;

import lombok.extern.slf4j.Slf4j;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Service
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ProductDocumentRepo productDocumentRepo;

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public ProductRepo getRepo() {
		return productRepo;
	}

	@Override
	public ProductMapper getMapper() {
		return productMapper;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Product create(ProductVo productVo) {
		Product product = productMapper.toNewEntity(productVo);

		product = productRepo.saveAndFlush(product);
		log.info("New Product({}) created", product.getId());

		// Sync to Elasticsearch
		ProductDocument document = mapToDocument(product);
		productDocumentRepo.save(document);

		return product;
	}

	@Override
	public ProductVo updateAndMapToVo(@Valid ProductVo productVo) {
		Assert.notNull(productVo.getId(), "Product id cannot be null");

		Product product = this.findOneByIdOrElseThrow(productVo.getId());

		product = productMapper.toExistingEntity(productVo, product);
		product = productRepo.saveAndFlush(product);

		// Sync to Elasticsearch
		ProductDocument document = mapToDocument(product);
		productDocumentRepo.save(document);

		refresh(product);

		return productMapper.toSemiDetailVo(product);
	}

	@Override
	public void deleteById(Long id) {
		Product product = this.findOneByIdOrElseThrow(id);

		product.setActive(false);

		product = productRepo.saveAndFlush(product);

		// Sync to Elasticsearch (soft delete by setting active=false)
		ProductDocument document = mapToDocument(product);
		productDocumentRepo.save(document);

		log.info("Soft Deleted Product({})", id);
	}

	@Override
	public Page<ProductVo> searchFromElasticsearch(ProductFilterParams params, Pageable pageable) {
		// Build Elasticsearch query based on filter params
		Query.Builder queryBuilder = new Query.Builder();

		if (params.getName() != null && !params.getName().isEmpty()) {
			queryBuilder.match(m -> m.field("name").query(params.getName()));
		}

		if (params.getMinUnitPrice() != null) {
			RangeQuery rangeQuery = RangeQuery.of(r -> r.field("unitPrice").gte(params.getMinUnitPrice().toString()));
			queryBuilder.range(rangeQuery);
		}

		if (params.getMaxUnitPrice() != null) {
			RangeQuery rangeQuery = RangeQuery.of(r -> r.field("unitPrice").lte(params.getMaxUnitPrice().toString()));
			queryBuilder.range(rangeQuery);
		}

		// Only active products
		TermQuery activeQuery = TermQuery.of(t -> t.field("active").value(true));
		queryBuilder.term(activeQuery);

		NativeQuery nativeQuery = NativeQuery.builder().withQuery(queryBuilder.build()).withPageable(pageable).build();

		SearchHits<ProductDocument> searchHits = elasticsearchOperations.search(nativeQuery, ProductDocument.class);

		List<ProductVo> productVos = searchHits.getSearchHits().stream().map(SearchHit::getContent)
				.map(this::mapDocumentToVo).collect(Collectors.toList());

		return new PageImpl<>(productVos, pageable, searchHits.getTotalHits());
	}

	private ProductDocument mapToDocument(Product product) {
		ProductDocument document = new ProductDocument();
		document.setId(product.getId());
		document.setName(product.getName());
		document.setUnitPrice(product.getUnitPrice());
		document.setActive(product.isActive());
		return document;
	}

	private ProductVo mapDocumentToVo(ProductDocument document) {
		ProductVo vo = new ProductVo();
		vo.setId(document.getId());
		vo.setName(document.getName());
		vo.setUnitPrice(document.getUnitPrice());
		vo.setActive(document.isActive());
		return vo;
	}

}