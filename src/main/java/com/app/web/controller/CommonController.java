package com.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.web.dto.PaginationInfo;
import com.app.web.dto.ResponseJson;
import com.app.web.dto.UserRequest;
import com.app.web.service.CommonService;
import com.app.web.service.PostService;
import com.app.web.service.TokenService;
import com.app.web.service.UserService;
import com.app.web.utils.MappingConstant;

@RestController
@RequestMapping(MappingConstant.COMMON)
public class CommonController {

	@Autowired
	private ResponseJson response;

	@Autowired
	private CommonService commonService;

	@Autowired
	private PostService postService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserService userService;

	@PostMapping(MappingConstant.TOKEN)
	public ResponseJson token(@RequestBody UserRequest userRequest) {
		response.setResponse(tokenService.generateUserToken(userRequest));
		return response;
	}

	@GetMapping(MappingConstant.VERIFICATION)
	public ResponseJson emailVerification(@RequestParam String email, @RequestParam String verificationKey) {
		response.setResponse(userService.emailVerification(email, verificationKey));
		return response;

	}

	@GetMapping(MappingConstant.MASTER_KEY)
	public ResponseJson getMasterKey() {
		response.setResponse(commonService.getMasterKey());
		return response;
	}

	@GetMapping(MappingConstant.POSTS)
	public ResponseJson getPosts(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurPage(pageNumber);
		paginationInfo.setPageSize(pageSize);
		response.setResponse(postService.getPosts(paginationInfo));
		return response;
	}

	@GetMapping(MappingConstant.TRENDING_POSTS)
	public ResponseJson getTrendingPosts(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurPage(pageNumber);
		paginationInfo.setPageSize(pageSize);
		response.setResponse(postService.getTrendingPosts(paginationInfo));
		return response;
	}

	@GetMapping(MappingConstant.BY_USER)
	public ResponseJson getPostsByUserId(@RequestParam Long userId, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurPage(pageNumber);
		paginationInfo.setPageSize(pageSize);
		response.setResponse(postService.getPostsByUserId(userId, paginationInfo, null));
		return response;
	}

	@GetMapping(MappingConstant.BY_TAG)
	public ResponseJson getPostByTagId(@RequestParam Long tagId, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurPage(pageNumber);
		paginationInfo.setPageSize(pageSize);
		response.setResponse(postService.getPostByTagId(tagId, paginationInfo, null));
		return response;
	}

	@GetMapping(MappingConstant.SEARCH)
	public ResponseJson getPostBySearch(@RequestParam String search) {
		response.setResponse(postService.getPostBySearch(search));
		return response;
	}

	@GetMapping(MappingConstant.COMMON_ID)
	public ResponseJson getPost(@PathVariable Long id) {
		response.setResponse(postService.getPost(id));
		return response;
	}

	@GetMapping(MappingConstant.COMMENT + MappingConstant.COMMON_ID + MappingConstant.SLASH_WITH_PAGE)
	public ResponseJson getComments(@PathVariable Long id, @PathVariable Integer pageNumber,
			@PathVariable Integer pageSize) {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurPage(pageNumber);
		paginationInfo.setPageSize(pageSize);
		response.setResponse(postService.getComments(null ,id, paginationInfo));
		return response;
	}

	@GetMapping(MappingConstant.HASHTAG + MappingConstant.SLASH)
	public ResponseJson getHashTags() {
		response.setResponse(postService.getHashTags());
		return response;
	}

}
