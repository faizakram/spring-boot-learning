package com.app.web.repository;

import java.util.List;

import com.app.web.dto.HashTagDTO;
import com.app.web.dto.PaginationInfo;
import com.app.web.dto.PostDTO;
import com.app.web.dto.UserRequest;
import com.app.web.model.Post;
import com.app.web.model.PostComment;

public interface CommonRepository {

	/**
	 * get Posts
	 * @param userId
	 * @param followingEnumCode
	 * @param paginationInfo
	 * @return
	 */
	List<Post> getPostsByLoggedInUser(Long userId, String followingEnumCode, PaginationInfo paginationInfo);
	/**
	 * getByUser
	 * @param search
	 * @param paginationInfo
	 * @return
	 */
	List<UserRequest> getByUser(String search);
	/**
	 * getPosts
	 * @param followingEnumCode
	 * @param paginationInfo
	 * @param istrending 
	 * @return
	 */
	List<Post> getPosts(PaginationInfo paginationInfo, boolean istrending);
	/**
	 * getByTag
	 * @param search
	 * @return
	 */
	List<HashTagDTO> getByTag(String search);
	/**getPostByTagId
	 * Get Post By Tag Id
	 * @param tagId
	 * @param paginationInfo
	 * @return
	 */
	List<Object[]> getPostByTagId(Long tagId, PaginationInfo paginationInfo);
	/**
	 * geByPost
	 * @param search
	 * @return
	 */
	List<PostDTO> geByPost(String search);
	/**
	 * 
	 * @param userId
	 * @param paginationInfo
	 * @return
	 */
	List<Object[]> getPostsByUserId(Long userId, PaginationInfo paginationInfo);
	/**
	 * getPostsQuickWay
	 * @param paginationInfo
	 * @param istrending
	 * @return
	 */
	List<Object[]> getPosts(PaginationInfo paginationInfo);
	/**
	 * getPostsQuickWay
	 * @param paginationInfo
	 * @param userId
	 * @return
	 */
	List<Object[]> getPostsUser(PaginationInfo paginationInfo, Long userId);
	/**
	 * getTrendingPostsQuickWay
	 * @param paginationInfo
	 * @return
	 */
	List<Object[]> getTrendingPosts(PaginationInfo paginationInfo);
	/**
	 * getTrendingPostsQuickWay
	 * @param paginationInfo
	 * @param userId
	 * @return
	 */
	List<Object[]> getTrendingPostsUser(PaginationInfo paginationInfo, Long userId);
	/**
	 * getPostsByUserId
	 * @param userId
	 * @param paginationInfo
	 * @param loggedInUserId
	 * @return
	 */
	List<Object[]> getPostsByUserId(Long userId, PaginationInfo paginationInfo, Long loggedInUserId);
	/**
	 * getPostByTagId
	 * @param tagId
	 * @param paginationInfo
	 * @param loggedInUserid
	 * @return
	 */
	List<Object[]> getPostByTagId(Long tagId, PaginationInfo paginationInfo, Long loggedInUserId);
	/**
	 * getPostById
	 * @param postId
	 * @param loggedInUserId
	 * @return
	 */
	Object[] getPostById(Long postId, Long loggedInUserId);
	/**
	 * getPostById
	 * @param postId
	 * @return
	 */
	Object[] getPostById(Long postId);
	/**
	 * getMyFavoritePosts
	 * @param userId
	 * @param paginationInfo
	 * @return
	 */
	List<Object[]> getMyFavoritePosts(Long userId, PaginationInfo paginationInfo);
	/**
	 * getComments
	 * @param postId
	 * @param asList
	 * @param paginationInfo 
	 * @return
	 */
	List<PostComment> getComments(Long postId, List<String> type, PaginationInfo paginationInfo);
	
	



}
