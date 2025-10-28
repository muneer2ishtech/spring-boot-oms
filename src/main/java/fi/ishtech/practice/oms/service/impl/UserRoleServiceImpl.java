package fi.ishtech.practice.oms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fi.ishtech.base.mapper.BaseStandardMapper;
import fi.ishtech.practice.oms.entity.User;
import fi.ishtech.practice.oms.entity.UserRole;
import fi.ishtech.practice.oms.enums.UserRoleEnum;
import fi.ishtech.practice.oms.repo.UserRoleRepo;
import fi.ishtech.practice.oms.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Service
@Slf4j
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleRepo userRoleRepo;

	@Override
	public UserRoleRepo getRepo() {
		return userRoleRepo;
	}

	@Override
	public BaseStandardMapper getMapper() {
		// this is needed when we have need admin can see and edit user roles on web page
		return null;
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public UserRole createForSignup(User user) {
		UserRole userRole = new UserRole();

		userRole.setUserId(user.getId());
		userRole.setUser(user);

		userRole.setRoleName(UserRoleEnum.USER);

		userRole.setActive(true);

		userRole = userRoleRepo.saveAndFlush(userRole);
		log.info("New UserRole({}) created for User({})", userRole.getId(), userRole.getUserId());

		return userRole;
	}

	@Override
	public List<UserRoleEnum> findAllActiveEnumsByUserId(Long userId) {
		return UserRoleEnum.valueOfOrElseNull(userRoleRepo.findAllRoleNamesByUserIdAndIsActiveTrue(userId));
	}

}