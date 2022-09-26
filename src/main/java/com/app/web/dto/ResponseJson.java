package com.app.web.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.app.web.utils.SuccessConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * Class for common response format
 *
 */
@Component
@JsonInclude(Include.NON_NULL)
@JsonSerialize(using = ResponseJsonSerializer.class)
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ResponseJson {

	@Autowired
	private Environment successProperty;

	private String responseCode;

	private String responseDescription;

	private Object response;

	public String getResponseCode() {
		if (this.responseCode == null) {
			responseCode = successProperty.getProperty(SuccessConstant.S0001_SUCCESS_CODE);
		}
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = successProperty.getProperty(responseCode);
	}

	public String getResponseDescription() {
		if (responseDescription == null) {
			responseDescription = successProperty.getProperty(SuccessConstant.S0001_SUCCESS_DESCRIPTION);
		}
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = successProperty.getProperty(responseDescription);
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseJson [successProperty=");
		builder.append(successProperty);
		builder.append(", responseCode=");
		builder.append(responseCode);
		builder.append(", responseDescription=");
		builder.append(responseDescription);
		builder.append(", response=");
		builder.append(response);
		builder.append("]");
		return builder.toString();
	}

}
