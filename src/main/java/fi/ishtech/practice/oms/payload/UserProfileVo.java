package fi.ishtech.practice.oms.payload;

import fi.ishtech.base.vo.BaseStandardNoIdEntityVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class UserProfileVo extends BaseStandardNoIdEntityVo {

	private static final long serialVersionUID = 5632154533700784881L;

	private Long id;

	@NotBlank
	private String firstName;

	private String middleName;

	@NotBlank
	private String lastName;

	private String title;

	private String prefix;

	private String suffix;

	private String mobilePhone;

	@Pattern(regexp = "[a-z]{2}")
	private String defaultLang;

	private Long addressId;

	private String email;

	public String getFullName() {
		if (middleName != null && !middleName.isBlank()) {
			return String.join(" ", firstName.strip(), middleName.strip(), lastName.strip());
		} else {
			return String.join(" ", firstName.strip(), lastName.strip());
		}
	}

}