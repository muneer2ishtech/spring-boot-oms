package fi.ishtech.practice.oms.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import fi.ishtech.base.entity.BaseStandardNoIdEntity;
import fi.ishtech.core.i18n.enums.LangEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Muneer Ahmed Syed
 */
@Entity
@Table(name = "t_user_profile")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserProfile extends BaseStandardNoIdEntity {

	private static final long serialVersionUID = -6592033605796475336L;

	@Id
	@Column(name = "id", nullable = false, insertable = true, updatable = false)
	private Long id;

	@Column(name = "first_name", nullable = false, insertable = true, updatable = true)
	private String firstName;

	@Column(name = "middle_name", nullable = true, insertable = true, updatable = true)
	private String middleName;

	@Column(name = "last_name", nullable = false, insertable = true, updatable = true)
	private String lastName;

	@Column(name = "title", nullable = true, insertable = true, updatable = true)
	private String title;

	@Column(name = "prefix", nullable = true, insertable = true, updatable = true)
	private String prefix;

	@Column(name = "suffix", nullable = true, insertable = true, updatable = true)
	private String suffix;

	@Column(name = "mobile_phone", nullable = true, insertable = true, updatable = true)
	private String mobilePhone;

	@Column(name = "default_lang", length = 2, nullable = false, insertable = true, updatable = true)
	private String defaultLang;

	@Column(name = "address_id", nullable = true, insertable = true, updatable = true)
	private Long addressId;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false,
			foreignKey = @ForeignKey(name = "fk_user_profile_user"))
	private User user;

	public String getFullName() {
		if (middleName != null && !middleName.isBlank()) {
			return String.join(" ", firstName.strip(), middleName.strip(), lastName.strip());
		} else {
			return String.join(" ", firstName.strip(), lastName.strip());
		}
	}

	public LangEnum getDefaultLangEnum() {
		return LangEnum.fromNameIgnoreCaseOrElseNull(this.defaultLang);
	}

	public String getEmail() {
		return getUser() == null ? null : user.getEmail();
	}

}