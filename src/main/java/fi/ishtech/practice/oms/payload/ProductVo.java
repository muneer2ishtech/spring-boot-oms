package fi.ishtech.practice.oms.payload;

import java.math.BigDecimal;

import fi.ishtech.base.vo.BaseStandardEntityVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ProductVo extends BaseStandardEntityVo {

	private static final long serialVersionUID = -547112558829425772L;

	@NotBlank
	private String name;

	@NotNull
	@Positive
	private BigDecimal unitPrice;

}