package com.app.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PostDTO {
	private Long id;
	private String title;
	private String description;
	private String mediaFileName;
	private List<Long> hashTagIds;
	private List<HashTagDTO> hashTags;
	private String startDate;
	private String expirationDate;
	private List<CommentDTO> comments;
	private boolean isPurchased;
	private Double fixedBid;
	private Double minBid;
	private Long bidTypeId;
	private Long type;
	private Long commentCount;
	private Long likeCount;
	private Long favouriteCount;
	private String postedOn;
	private Long followingId;
	private boolean following;
	private Long followerCount;
	private Long shareCount;
	private UserRequest user;
	private boolean isLiked;
	private boolean isFav;
	private Long likeId;
	private Long favouriteId;
	private boolean isPostOwner;
	
	public PostDTO(){
		this.commentCount = 0l;
		this.likeCount = 0l;
		this.favouriteCount = 0l;
		this.shareCount=0l;
	}
	
	public PostDTO(Long id, String title, String description, String mediaFileName) {
		this.id= id;
		this.title = title;
		this.description = description;
		this.mediaFileName = mediaFileName;
	}
	
}
