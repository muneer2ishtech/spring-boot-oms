package fi.ishtech.practice.oms.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
 * {@link Document} for Product(s)
 *
 * @author Muneer Ahmed Syed
 */
@Document(indexName = "products")
@Data
public class ProductDocument {

	@Id
	private Long id;

	@Field(type = FieldType.Text)
	private String name;

	@Field(type = FieldType.Double)
	private BigDecimal unitPrice;

	@Field(type = FieldType.Boolean)
	private boolean isActive;

	@Field(type = FieldType.Text)
	private String description;

}