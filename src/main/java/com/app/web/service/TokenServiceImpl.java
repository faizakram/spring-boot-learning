package com.app.web.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.app.web.dto.UserRequest;
import com.app.web.exception.ErrorCodeHelper;
import com.app.web.exception.ErrorInfo;
import com.app.web.exception.ServiceException;
import com.app.web.model.User;
import com.app.web.utils.CommonConstant;
import com.app.web.utils.DateUtil;
import com.app.web.utils.ErrorConstant;
import com.app.web.utils.TokenTime;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private ErrorCodeHelper errorCodeHelper;

	@Value("${spring.appliction.jwt.secret}")
	private String secret;
	@Value("${spring.appliction.jwt.minutes.validity}")
	private Integer jwtTokenValidity;
	@Autowired
	private TokenTime tokenTime;

	@Autowired
	private UserService userService;

	/**
	 * Tries to parse specified String as a JWT token. If successful, returns User
	 * object with user name, id and role pre-filled (extracted from token). If
	 * unsuccessful (token is invalid or not containing all required user
	 * properties), simply returns null.
	 * 
	 * @param token the JWT token to parse
	 * @return the User object extracted from specified token or null if a token is
	 *         invalid.
	 */

	@Override
	public Integer parseUserToken(String token) {
		try {
			Claims body = getAllClaimsFromToken(token);
			return (Integer) body.get(CommonConstant.USER_ID);
		} catch (ExpiredJwtException e) {
			ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(ErrorConstant.E1005_ERROR_CODE,
					ErrorConstant.E1005_ERROR_DESCRIPTION);
			throw new ServiceException(errorInfo, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(ErrorConstant.E1006_ERROR_CODE,
					ErrorConstant.E1006_ERROR_DESCRIPTION);
			throw new ServiceException(errorInfo, HttpStatus.UNAUTHORIZED);
		}

	}
	/**
	 * get All Claims From Token
	 * @param token
	 * @return
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).setClock(tokenTime).parseClaimsJws(token).getBody();
	}

	@Override
	public String generateUserToken(UserRequest userRequest) {
		if (ObjectUtils.isEmpty(userRequest.getMetaMaskId())) {
			ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(ErrorConstant.E1003_ERROR_CODE,
					ErrorConstant.E1003_ERROR_DESCRIPTION);
			throw new ServiceException(errorInfo, HttpStatus.UNAUTHORIZED);
		}
		User user = userService.getUser(userRequest.getMetaMaskId());
		if (user == null) {
			userRequest.setId(userService.addUser(userRequest));
		} else {
			userRequest.setId(user.getId());
		}
		Map<String, Object> claims = new HashMap<>();
		claims.put(CommonConstant.USER_ID, userRequest.getId());
		Timestamp currentTime = DateUtil.getCurrentTimestampInUTC();
		return Jwts.builder().setClaims(claims).setSubject(userRequest.getMetaMaskId()).setIssuedAt(currentTime)
				.setExpiration(DateUtil.addMinToCurrentUTCDate(currentTime, jwtTokenValidity))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}
