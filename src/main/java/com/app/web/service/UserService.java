package com.app.web.service;

import java.util.List;

import com.app.web.dto.UserRequest;
import com.app.web.model.User;

public interface UserService {
	/**
	 * UserRequest
	 * @param userRequest
	 * @return
	 */
	Long addUser(UserRequest userRequest);

	/**
	 * UserRequest
	 * @param userRequest
	 * @return
	 */
	Long updateUser(UserRequest userRequest);
	/**
	 * getUsers
	 * @param ids
	 * @return
	 */
	List<UserRequest> getUsers(List<Long> ids);
	/**
	 * 
	 * @param id
	 * @return
	 */
	UserRequest getUser(Long id);
	/**
	 * 	getUser
	 * @param metaMaskId
	 * @return
	 */
	User getUser(String metaMaskId);
	/**
	 * userRelation
	 * @param userId
	 * @param followerUserId
	 * @param masterKeyId
	 * @return
	 */
	Long userRelation(Long userId, Long followerUserId, Long masterKeyId);
	/**
	 * userRelationRevoke
	 * @param relationId
	 * @return
	 */
	boolean userRelationRevoke(Long relationId);
	/**
	 * getRelationVice
	 * @param userId
	 * @param masterKeyId
	 * @return
	 */
	List<UserRequest> getRelationVice(Long userId, Long masterKeyId);
	/**
	 * getRelationVersa
	 * @param userId
	 * @param masterKeyId
	 * @return
	 */
	List<UserRequest> getRelationVersa(Long userId, Long masterKeyId);
	/**
	 * emailVerification
	 * @param email
	 * @param verificationKey
	 * @return
	 */
	boolean emailVerification(String email, String verificationKey);
	/**
	 * getUserInfo
	 * @param user
	 * @param isfull
	 * @return
	 */
	UserRequest getUserInfo(User user, boolean isfull);
	/**
	 * updateTrendingUser
	 * @param userId
	 * @param typeId
	 * @return
	 */
	boolean updateTrendingUser(Long userId, Long typeId);

}
