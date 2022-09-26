package com.app.web.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.app.web.dto.UserRequest;
import com.app.web.exception.ErrorCodeHelper;
import com.app.web.exception.ErrorInfo;
import com.app.web.exception.ServiceException;
import com.app.web.model.EnumItemMaster;
import com.app.web.model.User;
import com.app.web.model.UserRelation;
import com.app.web.repository.UserRelationRepository;
import com.app.web.repository.UserRepository;
import com.app.web.utils.CommonConstant;
import com.app.web.utils.DateUtil;
import com.app.web.utils.ErrorConstant;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRelationRepository userRelationRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private CommonService commonService;

	@Autowired
	private ErrorCodeHelper errorCodeHelper;

	@Override
	public Long addUser(UserRequest userRequest) {
		User user = new User();
		user.setPostType(commonService.getEnumItem(CommonConstant.POST_ENUM_CODE, CommonConstant.POST_ENUM_ITEM_CODE));
		user.setMetaMaskId(userRequest.getMetaMaskId());
		return addOrUpdateUser(userRequest, user);
	}

	/**
	 * add Or UpdateUser
	 * 
	 * @param userRequest
	 * @param user
	 * @return
	 */
	private Long addOrUpdateUser(UserRequest userRequest, User user) {
		boolean isMailVerifiction = ObjectUtils.isEmpty(user.getEmail())
				&& !ObjectUtils.isEmpty(userRequest.getEmail());
		if (isMailVerifiction) {
			if (userRepository.isUserAlreadyExist(userRequest.getEmail())) {
				ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(ErrorConstant.E1010_ERROR_CODE,
						ErrorConstant.E1010_ERROR_DESCRIPTION, CommonConstant.USER);
				throw new ServiceException(errorInfo);
			}
			user.setEmailVerification(UUID.randomUUID().toString());
		}
		user.setBio(userRequest.getBio());
		user.setCreatedOn(DateUtil.getCurrentTimestampInUTC());
		user.setDisplayName(userRequest.getDisplayName());
		user.setEmail(userRequest.getEmail());
		user.setPersonalSite(userRequest.getPersonalSite());
		user.setProfileBanner(userRequest.getProfileBanner());
		user.setProfileImg(userRequest.getProfileImg());
		user.setTwitterUsername(userRequest.getTwitterUsername());
		user.setUsername(userRequest.getUserName());
		user.setUrl(userRequest.getUrl());
		user = userRepository.save(user);
		if (isMailVerifiction) {
			emailService.sendEmailVerification(user.getEmail(), user.getEmailVerification());
		}
		return user.getId();
	}

	@Override
	public Long updateUser(UserRequest userRequest) {
		return addOrUpdateUser(userRequest, getUserById(userRequest.getId()));
	}

	@Override
	public List<UserRequest> getUsers(List<Long> ids) {
		return userRepository.findAllById(ids).stream().map(m -> getUserInfo(m, false)).collect(Collectors.toList());
	}

	@Override
	public UserRequest getUser(Long id) {
		return getUserInfo(getUserById(id), true);
	}

	/**
	 * get User By Id
	 * 
	 * @param id
	 * @return
	 */
	private User getUserById(Long id) {
		return (User) commonService.isNotPresent(userRepository.findById(id), CommonConstant.USER);
	}

	@Override
	public UserRequest getUserInfo(User user, boolean isfull) {
		UserRequest userRequest = new UserRequest();
		userRequest.setId(user.getId());
		userRequest.setUserName(user.getUsername());
		userRequest.setBio(user.getBio());
		userRequest.setProfileImg(user.getProfileImg());
		userRequest.setEmail(user.getEmail());
		userRequest.setDisplayName(user.getDisplayName());
		userRequest.setPostType(user.getPostType().getId());
		userRequest.setVerified(ObjectUtils.nullSafeEquals(user.getEmailVerification(), CommonConstant.VERIFIED));
		if (isfull) {
			userRequest.setDoj(
					DateUtil.convertDateToString(CommonConstant.SIMPLE_DATE_FORMAT_MMMM_YYYY, user.getCreatedOn()));
			userRequest.setPersonalSite(user.getPersonalSite());
			userRequest.setProfileBanner(user.getProfileBanner());
			userRequest.setTwitterUsername(user.getTwitterUsername());
			userRequest.setUrl(user.getUrl());
		}
		return userRequest;
	}

	@Override
	public Long userRelation(Long userId, Long followerUserId, Long masterKeyId) {
		UserRelation userRelation = new UserRelation();
		userRelation.setUser(new User(userId));
		userRelation.setFollower(new User(followerUserId));
		userRelation.setEnumItemMaster(new EnumItemMaster(masterKeyId));
		userRelation.setCreatedOn(DateUtil.getCurrentTimestampInUTC());
		userRelation = userRelationRepository.save(userRelation);
		return userRelation.getId();
	}

	@Override
	public boolean userRelationRevoke(Long relationId) {
		userRelationRepository.delete(new UserRelation(relationId));
		return true;
	}

	@Override
	public List<UserRequest> getRelationVice(Long userId, Long masterKeyId) {
		return userRelationRepository.getRelationVice(userId, masterKeyId);
	}

	@Override
	public List<UserRequest> getRelationVersa(Long userId, Long masterKeyId) {
		return userRelationRepository.getRelationVersa(userId, masterKeyId);
	}

	@Override
	public boolean emailVerification(String email, String verificationKey) {
		User user = userRepository.findForEmailVerification(email, verificationKey);
		if (user == null) {
			ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(ErrorConstant.E1012_ERROR_CODE,
					ErrorConstant.E1012_ERROR_DESCRIPTION, CommonConstant.USER);
			throw new ServiceException(errorInfo);
		}
		user.setEmailVerification(CommonConstant.VERIFIED);
		userRepository.save(user);
		return true;
	}

	@Override
	public User getUser(String metaMaskId) {
		return userRepository.getUser(metaMaskId);
	}

	@Override
	public boolean updateTrendingUser(Long userId, Long typeId) {
		User user = getUserById(userId);
		EnumItemMaster enumItemMaster = new EnumItemMaster();
		enumItemMaster.setId(typeId);
		user.setPostType(enumItemMaster);
		userRepository.save(user);
		return true;
	}

}
