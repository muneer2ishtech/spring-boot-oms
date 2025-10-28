package fi.ishtech.practice.oms.payload.in;

import fi.ishtech.base.vo.BaseVo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Data
public class SigninRequest implements BaseVo {

	private static final long serialVersionUID = 6071040922303039192L;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@ToString.Exclude
	private String password;

}