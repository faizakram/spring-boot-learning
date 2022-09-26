package com.app.web.service;

import java.util.List;
import java.util.Map;

import com.app.web.dto.CommentDTO;
import com.app.web.dto.HashTagDTO;
import com.app.web.dto.PaginationInfo;
import com.app.web.dto.PostDTO;
import com.app.web.dto.SearchResult;

/**
 * Post Service
 * 
 * @author Faiz Akram
 *
 */
public interface PostService {
	/**
	 * add Post
	 * 
	 * @param id
	 * @param post
	 * @return
	 */
	Long addPost(Long userId, PostDTO post);

	/**
	 * update Post
	 * 
	 * @param userId
	 * @param post
	 * @return
	 */
	Long updatePost(Long userId, PostDTO post);

	/**
	 * get Post
	 * 
	 * @param id
	 * @param isEdit
	 * @param userId
	 * @return
	 */
	PostDTO getPost(Long id, Long userId);

	/**
	 * get Post
	 * 
	 * @param id
	 * @return
	 */
	PostDTO getPost(Long id);

	/**
	 * add Comment
	 * 
	 * @param userId
	 * @param commnet
	 * @return
	 */
	Long addComment(Long userId, CommentDTO commnet);

	/**
	 * update Comment
	 * 
	 * @param userId
	 * @param commnet
	 * @return
	 */
	Long updateComment(Long userId, CommentDTO commnet);

	/**
	 * get Comments
	 * 
	 * @param id
	 * @param paginationInfo
	 * @return
	 */
	SearchResult<CommentDTO> getComments(Long userId,Long id, PaginationInfo paginationInfo);

	/**
	 * add Hash Tag
	 * 
	 * @param userId
	 * @param hashTag
	 * @return
	 */
	HashTagDTO addHashTag(Long userId, HashTagDTO hashTag);

	/**
	 * get Hash Tags
	 * 
	 * @return
	 */
	List<HashTagDTO> getHashTags();

	/**
	 * delete Post
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	boolean deletePost(Long userId, Long postId);

	/**
	 * deleteComment
	 * 
	 * @param userId
	 * @param id
	 * @return
	 */
	boolean deleteComment(Long userId, Long id);

	/**
	 * get My Favourite Posts
	 * 
	 * @param userId
	 * @param paginationInfo
	 * @param masterKeyId
	 * @return
	 */
	SearchResult<PostDTO> getMyFavoritePosts(Long userId, PaginationInfo paginationInfo);

	/**
	 * get Post By Search
	 * 
	 * @param userId
	 * @param search
	 * @param type
	 * @return
	 */
	Map<String, Object> getPostBySearch(String search);

	/**
	 * 
	 * @param tagId
	 * @param paginationInfo
	 * @param loggedInUserId
	 * @return
	 */
	SearchResult<PostDTO> getPostByTagId(Long tagId, PaginationInfo paginationInfo, Long loggedInUserId);

	/**
	 * deleteHashTag
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	boolean deleteHashTag(Long id, Long userId);

	/**
	 * get Posts By UserId
	 * 
	 * @param userId
	 * @param paginationInfo
	 * @param loggedInUserId
	 * @return
	 */
	SearchResult<PostDTO> getPostsByUserId(Long userId, PaginationInfo paginationInfo, Long loggedInUserId);

	/**
	 * getPosts
	 * 
	 * @param paginationInfo
	 * @return
	 */
	SearchResult<PostDTO> getPosts(PaginationInfo paginationInfo);

	/**
	 * getPostsUser
	 * 
	 * @param paginationInfo
	 * @param userId
	 * @return
	 */
	SearchResult<PostDTO> getPostsUser(PaginationInfo paginationInfo, Long userId);

	/**
	 * getTrendingPosts
	 * 
	 * @param paginationInfo
	 * @return
	 */
	SearchResult<PostDTO> getTrendingPosts(PaginationInfo paginationInfo);

	/**
	 * getTrendingPostsUser
	 * 
	 * @param paginationInfo
	 * @param userId
	 * @return
	 */
	SearchResult<PostDTO> getTrendingPostsUser(PaginationInfo paginationInfo, Long userId);

}
