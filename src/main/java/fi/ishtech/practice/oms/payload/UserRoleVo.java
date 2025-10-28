package fi.ishtech.practice.oms.payload;

import fi.ishtech.base.vo.BaseStandardEntityVo;
import fi.ishtech.practice.oms.enums.UserRoleEnum;
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
public class UserRoleVo extends BaseStandardEntityVo {

	private static final long serialVersionUID = -8046866314012852584L;

	private Long userId;

	private UserRoleEnum roleName;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private UserVo user;

}