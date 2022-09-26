package com.app.web.service;

import com.app.web.dto.UserRequest;

public interface TokenService {

	Integer parseUserToken(String token);

	String generateUserToken(UserRequest userRequest);

}
