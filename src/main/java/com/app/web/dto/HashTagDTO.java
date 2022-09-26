package com.app.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class HashTagDTO {

	private Long id;
	private String tagName;
	private Integer count;
	
	public HashTagDTO() {
	}
	
	public HashTagDTO(Long id, String tagName) {
		this.id = id;
		this.tagName = tagName;
	}
	public HashTagDTO(Long id, String tagName, Integer count) {
		this.id = id;
		this.tagName = tagName;
		this.count = count;
	}
}
