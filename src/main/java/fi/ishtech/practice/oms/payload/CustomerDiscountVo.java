package fi.ishtech.practice.oms.payload;

import java.math.BigDecimal;

import fi.ishtech.base.vo.BaseStandardEntityVo;
import fi.ishtech.practice.oms.enums.DiscountTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerDiscountVo extends BaseStandardEntityVo {

	private static final long serialVersionUID = 2888935904071079798L;

	@NotNull
	private Long customerId;

	private Long productId;

	@NotNull
	private DiscountTypeEnum discountType;

	private BigDecimal discountPercent;

	private Integer buyQuantity;

	private Integer payQuantity;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private UserProfileVo customer;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private ProductVo product;

}