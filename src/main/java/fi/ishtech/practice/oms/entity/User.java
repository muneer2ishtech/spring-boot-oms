package fi.ishtech.practice.oms.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fi.ishtech.base.entity.BaseStandardEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Entity
@Table(name = "t_user",
		uniqueConstraints = { @UniqueConstraint(name = "uk_user_username", columnNames = { "username" }),
				@UniqueConstraint(name = "uk_user_email", columnNames = { "email" }) })
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseStandardEntity {

	private static final long serialVersionUID = 3460606587225411632L;

	@Column(nullable = false, updatable = false, unique = true)
	private String username;

	@Column(nullable = false, updatable = false, unique = true)
	private String email;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	@Column(name = "password_hash", nullable = false, updatable = false)
	private String passwordHash;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	@Column(name = "password_reset_token", nullable = true, insertable = false, updatable = true)
	private String passwordResetToken;

	@Column(name = "force_change_password", nullable = true, insertable = true, updatable = true)
	private boolean forceChangePassword;

	@Column(name = "is_email_verified", nullable = true, insertable = true, updatable = false)
	private boolean isEmailVerified;

}