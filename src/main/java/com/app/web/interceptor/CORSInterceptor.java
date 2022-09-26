package com.app.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import com.app.web.utils.CommonConstant;

/**
 * Provide CORS origin support to application
 *
 */
@Component
public class CORSInterceptor implements AsyncHandlerInterceptor {
	/**
	 * Set CORS properties in the header of response
	 * 
	 * @return true if properties are set
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, HEAD, OPTIONS, PUT, DELETE");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
				"Content-Type, X-Requested-With,X-Auth-Token, accept,ticket, Origin,Access-Control-Request-Method, Access-Control-Request-Headers");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
				"Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization, Content-Disposition");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "7200");
		response.setHeader(CommonConstant.X_FRAME_OPTIONS, "DENY");
		response.setHeader(CommonConstant.X_CONTENT_TYPE_OPTIONS, "nosniff");
		response.setHeader(CommonConstant.STRICT_TRANSPORT_SECURITY, "max-age=31536000;includeSubDomains");
		response.setHeader(CommonConstant.CONTENT_SECURITY_POLICY, "script-src 'self'");
		response.setHeader(CommonConstant.X_PERMITTED_CROSS_DOMAIN_POLICIES, " master-only");

		return true;
	}
}
