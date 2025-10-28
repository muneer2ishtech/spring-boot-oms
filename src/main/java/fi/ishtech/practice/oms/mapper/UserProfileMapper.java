package fi.ishtech.practice.oms.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fi.ishtech.base.mapper.BaseStandardNoIdMapper;
import fi.ishtech.core.i18n.enums.LangEnum;
import fi.ishtech.practice.oms.entity.UserProfile;
import fi.ishtech.practice.oms.payload.UserProfileVo;
import fi.ishtech.practice.oms.payload.in.SignupRequest;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper extends BaseStandardNoIdMapper {

	/**
	 *
	 * @param entity {@link UserProfile}
	 * @return {@link UserProfileVo}
	 */
	@BeanMapping(ignoreByDefault = true)
	@InheritConfiguration(name = "toBaseStandardNoIdVo")
	@Mapping(source = "id", target = "id")
	@Mapping(source = "firstName", target = "firstName")
	@Mapping(source = "middleName", target = "middleName")
	@Mapping(source = "lastName", target = "lastName")
	@Mapping(source = "title", target = "title")
	@Mapping(source = "prefix", target = "prefix")
	@Mapping(source = "suffix", target = "suffix")
	@Mapping(source = "mobilePhone", target = "mobilePhone")
	@Mapping(source = "defaultLang", target = "defaultLang")
	@Mapping(source = "addressId", target = "addressId")
	@Mapping(source = "email", target = "email")
	UserProfileVo toBriefVo(UserProfile entity);

	/**
	 *
	 * @see {@link LangEnum}
	 *
	 * @param signupRequest
	 * @return new {@link UserProfile} entity
	 */
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "active", constant = "true")
	@Mapping(source = "firstName", target = "firstName")
	@Mapping(source = "lastName", target = "lastName")
	@Mapping(source = "lang", target = "defaultLang", defaultValue = "en")
	UserProfile toNewEntity(SignupRequest signupRequest);

}