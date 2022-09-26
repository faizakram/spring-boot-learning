package com.app.web.dto;

import lombok.Data;

/**
 * Page info to be sent with response
 * 
 */
@Data
public class PaginationInfo {

    private Integer pageSize;
    private Integer curPage;
    public PaginationInfo() {
    }
	public PaginationInfo(Integer pageSize, Integer curPage) {
		this.pageSize = pageSize;
		this.curPage = curPage;
	}

}