package com.app.web.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserRelationDTO {
	private Long id;
	private List<UserRequest> users;
	
}
