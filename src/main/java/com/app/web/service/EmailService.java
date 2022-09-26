package com.app.web.service;

public interface EmailService {

	/**
	 * Send Email Verification
	 * @param email
	 * @param emailVerification
	 * @return
	 */
	public boolean sendEmailVerification(String email, String emailVerification);
}
