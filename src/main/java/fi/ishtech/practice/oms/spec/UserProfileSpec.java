package fi.ishtech.practice.oms.spec;

import java.util.List;

import org.springframework.util.StringUtils;

import fi.ishtech.base.spec.BaseStandardNoIdSpec;
import fi.ishtech.practice.oms.entity.User;
import fi.ishtech.practice.oms.entity.UserProfile;
import fi.ishtech.practice.oms.entity.UserProfile_;
import fi.ishtech.practice.oms.entity.User_;
import fi.ishtech.practice.oms.payload.filter.UserProfileFilterParams;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 *
 * @author Muneer Ahmed Syed
 */
public class UserProfileSpec extends BaseStandardNoIdSpec<UserProfile, UserProfileFilterParams> {

	private static final long serialVersionUID = -2080551823386644045L;

	public UserProfileSpec(UserProfileFilterParams params) {
		super(params);
	}

	@Override
	protected List<Predicate> toPredicateList(Root<UserProfile> root, CriteriaBuilder cb) {
		List<Predicate> predicates = super.toPredicateList(root, cb);

		addPredicateLike(predicates, root, cb, getParams().getFirstName(), UserProfile_.FIRST_NAME);
		addPredicateLike(predicates, root, cb, getParams().getMiddleName(), UserProfile_.MIDDLE_NAME);
		addPredicateLike(predicates, root, cb, getParams().getLastName(), UserProfile_.LAST_NAME);
		addPredicateLike(predicates, root, cb, getParams().getTitle(), UserProfile_.TITLE);
		addPredicateLike(predicates, root, cb, getParams().getPrefix(), UserProfile_.PREFIX);
		addPredicateLike(predicates, root, cb, getParams().getSuffix(), UserProfile_.SUFFIX);

		addPredicateLike(predicates, root, cb, getParams().getMobilePhone(), UserProfile_.MOBILE_PHONE);
		addPredicateEq(predicates, root, cb, getParams().getDefaultLang(), UserProfile_.DEFAULT_LANG);
		addPredicateEq(predicates, root, cb, getParams().getAddressId(), UserProfile_.ADDRESS_ID);

		if (StringUtils.hasText(getParams().getEmail())) {
			Join<UserProfile, User> joinUser = getJoin(null, root, UserProfile_.USER);
			addPredicateLike(predicates, joinUser, cb, getParams().getEmail(), User_.EMAIL);
		}

		return predicates;
	}

}