package com.app.web.dto;

import lombok.Data;

@Data
public class CommonDTO {
	private Long id;
	private String name;
	public CommonDTO() {
		
	}
	public CommonDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
