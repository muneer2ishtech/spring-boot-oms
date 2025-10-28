package fi.ishtech.practice.oms.payload.in;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fi.ishtech.base.vo.BaseVo;
import fi.ishtech.core.i18n.enums.LangEnum;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Data
@ToString(callSuper = true)
public class SignupRequest implements BaseVo {

	private static final long serialVersionUID = 8546768776426449769L;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	@Email
	private String email;

	@ToString.Exclude
	@NotBlank
	private String password;

	@ToString.Exclude
	@NotBlank
	private String passwordConfirm;

	@AssertTrue(message = "password and passwordConfirm are not matching")
	@JsonIgnore
	public boolean isPasswordAndPasswordConfirmMatch() {
		return password.equals(passwordConfirm);
	}

	@AssertTrue
	private boolean acceptTermsConditions;

	private LangEnum lang;

}