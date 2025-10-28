package fi.ishtech.practice.oms.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fi.ishtech.base.repo.BaseStandardRepo;
import fi.ishtech.practice.oms.entity.UserRole;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface UserRoleRepo extends BaseStandardRepo<UserRole> {

	List<UserRole> findAllByUserId(Long userId);

	Optional<UserRole> findOneByUserIdAndRoleName(Long userId, String roleName);

	@Query("SELECT ur.roleName FROM UserRole ur WHERE ur.userId = :userId AND ur.isActive = true")
	List<String> findAllRoleNamesByUserIdAndIsActiveTrue(@Param("userId") Long userId);

}