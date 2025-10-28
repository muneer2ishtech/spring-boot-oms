package fi.ishtech.practice.oms.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import fi.ishtech.base.entity.BaseStandardEntity;
import fi.ishtech.practice.oms.enums.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "t_user_role",
		uniqueConstraints = {
				@UniqueConstraint(name = "uk_user_role_user_id_role", columnNames = { "user_id", "role_name" }) })
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserRole extends BaseStandardEntity {

	private static final long serialVersionUID = 4628140404447676736L;

	@Column(name = "user_id", nullable = false, insertable = true, updatable = false)
	private Long userId;

	@Column(name = "role_name", nullable = false, insertable = true, updatable = false,
			columnDefinition = "enum('ADMIN', 'USER')")
	@Enumerated(EnumType.STRING)
	private UserRoleEnum roleName;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false,
			foreignKey = @ForeignKey(name = "fk_user_role_user"))
	private User user;

}