package fi.ishtech.practice.oms.payload;

import java.math.BigDecimal;
import java.util.Set;

import fi.ishtech.base.vo.BaseStandardEntityVo;
import jakarta.validation.Valid;
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
public class SalesOrderVo extends BaseStandardEntityVo {

	private static final long serialVersionUID = -4746775357482769436L;

	private Long customerId;

	private BigDecimal origTotalAmount;

	private BigDecimal totalAmount;

	private BigDecimal discountPercent;

	private BigDecimal discountAmount;

	private BigDecimal netAmount;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private UserProfileVo customer;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Valid
	private Set<SalesOrderItemVo> salesOrderItems;

}