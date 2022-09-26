package com.app.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CommentDTO {
	private Long id;
	private Long type;
	private Long postId;
	private String comment;
	private Long parentCommentId;
	private String commentDate;
	List<CommentDTO> comments;
	private boolean isCommentOwner;
	private UserRequest user;
}
