package com.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.web.dto.ResponseJson;
import com.app.web.dto.UserRequest;
import com.app.web.service.UserService;
import com.app.web.utils.CommonConstant;
import com.app.web.utils.MappingConstant;

@RestController
@RequestMapping(MappingConstant.USER)
public class UserController {
	@Autowired
	private ResponseJson response;

	@Autowired
	private UserService userService;

	@GetMapping(MappingConstant.SLASH)
	public ResponseJson getUser(@RequestAttribute(CommonConstant.USER_ID) Long userId) {
		response.setResponse(userService.getUser(userId));
		return response;
	}

	@PutMapping(MappingConstant.UPDATE)
	public ResponseJson updateUser(@RequestAttribute(CommonConstant.USER_ID) Long userId,
			@RequestBody UserRequest userRequest) {
		userRequest.setId(userId);
		response.setResponse(userService.updateUser(userRequest));
		return response;
	}
	
	@PutMapping(MappingConstant.UPDATE_TRENDING)
	public ResponseJson updateTrendingUser(@RequestAttribute(CommonConstant.USER_ID) Long userId,
			@PathVariable(CommonConstant.ID) Long typeId) {
		response.setResponse(userService.updateTrendingUser(userId,typeId));
		return response;
	}

	@PutMapping({ MappingConstant.FOLLOWING, MappingConstant.BLOCKED })
	public ResponseJson userRelation(@RequestAttribute(CommonConstant.USER_ID) Long userId,
			@RequestParam(CommonConstant.USER_ID) Long followerUserId, @RequestParam Long masterKeyId) {
		response.setResponse(userService.userRelation(userId, followerUserId, masterKeyId));
		return response;
	}

	@DeleteMapping({ MappingConstant.FOLLOW + MappingConstant.COMMON_ID,
			MappingConstant.FOLLOWING + MappingConstant.COMMON_ID,
			MappingConstant.BLOCKED + MappingConstant.COMMON_ID })
	public ResponseJson userRelationRevoke(@PathVariable(CommonConstant.ID) Long relationId) {
		response.setResponse(userService.userRelationRevoke(relationId));
		return response;
	}

	@GetMapping({ MappingConstant.FOLLOWING + MappingConstant.COMMON_ID,
			MappingConstant.BLOCKED + MappingConstant.COMMON_ID})
	public ResponseJson getRelationVice(@RequestAttribute(CommonConstant.USER_ID) Long userId,
			@PathVariable(CommonConstant.ID) Long masterKeyId) {
		response.setResponse(userService.getRelationVice(userId, masterKeyId));
		return response;
	}

	@GetMapping(MappingConstant.FOLLOWER + MappingConstant.COMMON_ID)
	public ResponseJson getRelationVersa(@RequestAttribute(CommonConstant.USER_ID) Long userId,
			@PathVariable(CommonConstant.ID) Long masterKeyId) {
		response.setResponse(userService.getRelationVersa(userId, masterKeyId));
		return response;
	}

}
