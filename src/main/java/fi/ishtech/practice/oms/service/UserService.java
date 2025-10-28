package fi.ishtech.practice.oms.service;

import java.util.Optional;

import fi.ishtech.base.service.BaseStandardService;
import fi.ishtech.practice.oms.entity.User;
import fi.ishtech.practice.oms.payload.UserVo;
import fi.ishtech.practice.oms.payload.in.SignupRequest;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface UserService extends BaseStandardService<User, UserVo> {

	Optional<User> findOneByEmail(String email);

	User create(SignupRequest signupRequest);

}