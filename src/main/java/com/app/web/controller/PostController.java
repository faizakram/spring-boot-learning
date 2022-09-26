package com.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.web.dto.CommentDTO;
import com.app.web.dto.HashTagDTO;
import com.app.web.dto.PaginationInfo;
import com.app.web.dto.PostDTO;
import com.app.web.dto.ResponseJson;
import com.app.web.service.PostService;
import com.app.web.utils.CommonConstant;
import com.app.web.utils.MappingConstant;

@RestController
@RequestMapping(MappingConstant.POST)
public class PostController {

	@Autowired
	private ResponseJson response;

	@Autowired
	private PostService postService;

	@GetMapping(MappingConstant.SLASH_WITH_PAGE)
	public ResponseJson getMyPosts(@RequestAttribute(CommonConstant.USER_ID) Long userId,
			@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurPage(pageNumber);
		paginationInfo.setPageSize(pageSize);
		response.setResponse(postService.getPostsByUserId(userId, paginationInfo, userId));
		return response;
	}

	@GetMapping(MappingConstant.FAVOURITES)
	public ResponseJson getMyFavoritePosts(@RequestAttribute(CommonConstant.USER_ID) Long userId,
			@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurPage(pageNumber);
		paginationInfo.setPageSize(pageSize);
		response.setResponse(postService.getMyFavoritePosts(userId, paginationInfo));
		return response;
	}

	@GetMapping(MappingConstant.COMMON_ID)
	public ResponseJson getPost(@RequestAttribute(CommonConstant.USER_ID) Long userId, @PathVariable Long id) {
		response.setResponse(postService.getPost(id, userId));
		return response;
	}

	@PostMapping(MappingConstant.ADD)
	public ResponseJson addPost(@RequestAttribute(CommonConstant.USER_ID) Long userId, @RequestBody PostDTO post) {
		response.setResponse(postService.addPost(userId, post));
		return response;
	}

	@PutMapping(MappingConstant.UPDATE)
	public ResponseJson updatePost(@RequestAttribute(CommonConstant.USER_ID) Long userId, @RequestBody PostDTO post) {
		response.setResponse(postService.updatePost(userId, post));
		return response;
	}

	@DeleteMapping(MappingConstant.COMMON_ID)
	public ResponseJson deletePost(@RequestAttribute(CommonConstant.USER_ID) Long userId, @PathVariable Long id) {
		response.setResponse(postService.deletePost(userId, id));
		return response;
	}

	@GetMapping(MappingConstant.COMMENT + MappingConstant.COMMON_ID + MappingConstant.SLASH_WITH_PAGE)
	public ResponseJson getComments(@RequestAttribute(CommonConstant.USER_ID) Long userId, @PathVariable Long id,
			@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurPage(pageNumber);
		paginationInfo.setPageSize(pageSize);
		response.setResponse(postService.getComments(userId, id, paginationInfo));
		return response;
	}

	@PostMapping({ MappingConstant.COMMENT + MappingConstant.ADD, MappingConstant.FAVOURITE + MappingConstant.ADD,
			MappingConstant.LIKE + MappingConstant.ADD })
	public ResponseJson addComment(@RequestAttribute(CommonConstant.USER_ID) Long userId,
			@RequestBody CommentDTO commnet) {
		response.setResponse(postService.addComment(userId, commnet));
		return response;
	}

	@PutMapping(MappingConstant.COMMENT + MappingConstant.UPDATE)
	public ResponseJson updateComment(@RequestAttribute(CommonConstant.USER_ID) Long userId,
			@RequestBody CommentDTO commnet) {
		response.setResponse(postService.updateComment(userId, commnet));
		return response;
	}

	@DeleteMapping({ MappingConstant.COMMENT + MappingConstant.COMMON_ID,
			MappingConstant.LIKE + MappingConstant.COMMON_ID, MappingConstant.FAVOURITE + MappingConstant.COMMON_ID })
	public ResponseJson deleteComment(@RequestAttribute(CommonConstant.USER_ID) Long userId, @PathVariable Long id) {
		response.setResponse(postService.deleteComment(userId, id));
		return response;
	}

	@PostMapping(MappingConstant.HASHTAG)
	public ResponseJson addHashTag(@RequestAttribute(CommonConstant.USER_ID) Long userId,
			@RequestBody HashTagDTO hashTag) {
		response.setResponse(postService.addHashTag(userId, hashTag));
		return response;
	}

	@DeleteMapping(MappingConstant.HASHTAG + MappingConstant.COMMON_ID)
	public ResponseJson deleteHashTag(@RequestAttribute(CommonConstant.USER_ID) Long userId, @PathVariable Long id) {
		response.setResponse(postService.deleteHashTag(id, userId));
		return response;
	}

	@GetMapping(MappingConstant.POSTS_USER)
	public ResponseJson getPostsUser(@PathVariable Integer pageNumber, @PathVariable Integer pageSize,
			@RequestAttribute(CommonConstant.USER_ID) Long userId) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurPage(pageNumber);
		paginationInfo.setPageSize(pageSize);
		response.setResponse(postService.getPostsUser(paginationInfo, userId));
		return response;
	}

	@GetMapping(MappingConstant.TRENDING_POSTS_USER)
	public ResponseJson getTrendingPostsUser(@PathVariable Integer pageNumber, @PathVariable Integer pageSize,
			@RequestAttribute(CommonConstant.USER_ID) Long userId) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurPage(pageNumber);
		paginationInfo.setPageSize(pageSize);
		response.setResponse(postService.getTrendingPostsUser(paginationInfo, userId));
		return response;
	}

	@GetMapping(MappingConstant.BY_USER)
	public ResponseJson getPostsByUserId(@RequestParam Long userId, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize, @RequestAttribute(CommonConstant.USER_ID) Long loggedInUserId) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurPage(pageNumber);
		paginationInfo.setPageSize(pageSize);
		response.setResponse(postService.getPostsByUserId(userId, paginationInfo, loggedInUserId));
		return response;
	}

	@GetMapping(MappingConstant.BY_TAG)
	public ResponseJson getPostByTagId(@RequestParam Long tagId, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize, @RequestAttribute(CommonConstant.USER_ID) Long loggedInUserId) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurPage(pageNumber);
		paginationInfo.setPageSize(pageSize);
		response.setResponse(postService.getPostByTagId(tagId, paginationInfo, loggedInUserId));
		return response;
	}

}
