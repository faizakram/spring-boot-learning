package com.app.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.app.web.exception.ErrorCodeHelper;
import com.app.web.exception.ErrorInfo;
import com.app.web.exception.ServiceException;
import com.app.web.service.TokenService;
import com.app.web.utils.CommonConstant;
import com.app.web.utils.ErrorConstant;

@Component
public class BasicInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;
	@Autowired
	private ErrorCodeHelper errorCodeHelper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader(CommonConstant.HEADER_TOKEN);
		if (handler instanceof HandlerMethod) {
			if (token == null) {
				ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(ErrorConstant.E1004_ERROR_CODE,
						ErrorConstant.E1004_ERROR_DESCRIPTION);
				throw new ServiceException(errorInfo, HttpStatus.UNAUTHORIZED);
			} else {
				request.setAttribute(CommonConstant.USER_ID, tokenService.parseUserToken(token));
				return true;
			}
		}
		return false;
	}

}
