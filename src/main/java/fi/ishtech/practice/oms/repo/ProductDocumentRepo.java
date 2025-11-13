package fi.ishtech.practice.oms.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import fi.ishtech.practice.oms.entity.ProductDocument;

/**
 * Repo for ElasticSearch of ProductDocument
 *
 * @author Muneer Ahmed Syed
 */
public interface ProductDocumentRepo extends ElasticsearchRepository<ProductDocument, Long> {

}