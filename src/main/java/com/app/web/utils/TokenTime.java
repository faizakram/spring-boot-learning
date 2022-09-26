package com.app.web.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Clock;

@Component
public class TokenTime implements Clock {
	@Override
	public Date now() {
		return DateUtil.getCurrentTimestampInUTC();
	}

}
