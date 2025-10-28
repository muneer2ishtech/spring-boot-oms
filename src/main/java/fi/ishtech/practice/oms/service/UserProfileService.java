package fi.ishtech.practice.oms.service;

import fi.ishtech.base.service.BaseStandardNoIdService;
import fi.ishtech.practice.oms.entity.UserProfile;
import fi.ishtech.practice.oms.payload.UserProfileVo;
import fi.ishtech.practice.oms.payload.in.SignupRequest;

/**
 *
 * @author Muneer Ahmed Syed
 */
public interface UserProfileService extends BaseStandardNoIdService<UserProfile, UserProfileVo, Long> {

	/**
	 * Creates User and UserProfile and default role
	 *
	 * @param signupRequest - {@link SignupRequest}
	 * @return {@link Long} - user id
	 */
	Long create(SignupRequest signupRequest);

}