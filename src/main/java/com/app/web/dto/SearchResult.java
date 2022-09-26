package com.app.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
@Data
@JsonInclude(value = Include.NON_NULL)
public class SearchResult<T> {
	private List<T> results;
	private PaginationInfo paginationInfo;
	
	public SearchResult(List<T> results, PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
		this.results = results;
	}
}
