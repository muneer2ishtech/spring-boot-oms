package fi.ishtech.practice.oms.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fi.ishtech.base.mapper.BaseStandardMapper;
import fi.ishtech.practice.oms.entity.User;
import fi.ishtech.practice.oms.payload.UserVo;
import fi.ishtech.practice.oms.payload.in.SignupRequest;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends BaseStandardMapper {

	/**
	 *
	 * passwordHash should not be mapped<br>
	 * passwordResetToken should not be mapped<br>
	 *
	 * @param entity - {@link User}
	 *
	 * @return {@link UserVo}
	 */
	@BeanMapping(ignoreByDefault = true)
	@InheritConfiguration(name = "toBaseStandardVo")
	@Mapping(source = "username", target = "username")
	@Mapping(source = "email", target = "email")
	@Mapping(source = "forceChangePassword", target = "forceChangePassword")
	@Mapping(source = "emailVerified", target = "emailVerified")
	UserVo toBriefVo(User entity);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "email", target = "email")
	@Mapping(target = "forceChangePassword", constant = "false")
	@Mapping(target = "emailVerified", constant = "false")
	@Mapping(target = "active", constant = "true")
	User toNewEntity(SignupRequest signupRequest);

}