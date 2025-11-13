package fi.ishtech.practice.oms.service;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fi.ishtech.base.service.BaseStandardService;
import fi.ishtech.practice.oms.entity.Product;
import fi.ishtech.practice.oms.payload.ProductVo;
import fi.ishtech.practice.oms.payload.filter.ProductFilterParams;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface ProductService extends BaseStandardService<Product, ProductVo> {

	/**
	 * Creates a new Product
	 *
	 * @param productVo - {@link ProductVo}
	 * @return {@link Product}
	 */
	Product create(ProductVo productVo);

	ProductVo updateAndMapToVo(@Valid ProductVo productVo);

	/**
	 * Soft deletes Product
	 *
	 * @param id
	 */
	void deactivateById(Long id);

	/**
	 * Searches products from Elasticsearch based on filter params.
	 *
	 * @param params   - {@link ProductFilterParams}
	 * @param pageable - {@link Pageable}
	 * @return {@link Page<ProductVo>}
	 */
	Page<ProductVo> searchFromElasticsearch(ProductFilterParams params, Pageable pageable);

}