package com.app.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UserRequest {

	private Long id;
	private String metaMaskId;
	private String email;
	private String userName;
	private String displayName;
	private String bio;
	private String personalSite;
	private String profileBanner;
	private String profileImg;
	private String twitterUsername;
	private String url;
	private String doj;
	private Long relationId;
	private boolean verified;
	private Long postType;

	public UserRequest() {
		this.verified = false;
	}

	public UserRequest(Long id, String userName, String displayName, String bio, String profileImg, Long relationId, boolean verified) {
		super();
		this.id = id;
		this.userName = userName;
		this.displayName = displayName;
		this.bio = bio;
		this.profileImg = profileImg;
		this.relationId = relationId;
		this.verified = verified;
	}

	public UserRequest(Long id, String userName, String displayName, String profileImg, boolean verified) {
		super();
		this.id = id;
		this.userName = userName;
		this.displayName = displayName;
		this.profileImg = profileImg;
		this.verified = verified;
	}

}
