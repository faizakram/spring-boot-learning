package com.app.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.app.web.utils.CommonConstant;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private FreeMarkerConfigurer freemarkerConfigurer;

	@Value("${spring.application.ui.base.url}")
	private String uiBaseURL;
	@Value("${spring.mail.service.enable}")
	boolean isMailServiceEnable;
	@Value("${spring.mail.email.from}")
	private String noReplyEmail;

	@Override
	public boolean sendEmailVerification(String email, String emailVerification) {
		Map<String, Object> templateModel = new HashMap<>();
		templateModel.put("verificationURL",
				uiBaseURL + String.format("/verification?emailVerificationKey=%s&email=%s", emailVerification, email));
		String htmlBody = sendMessageUsingFreemarkerTemplate(templateModel, "template-freemarker.ftl");
		if (ObjectUtils.isEmpty(htmlBody))
			return false;
		return sendHtmlMessage(email, CommonConstant.EMAIL_VERIFICATION_SUBJECT, htmlBody, null);
	}

	/**
	 * send Message Using Freemarker Template
	 * 
	 * @param to
	 * @param subject
	 * @param templateModel
	 * @param file
	 * @return
	 */
	public String sendMessageUsingFreemarkerTemplate(Map<String, Object> templateModel, String templateName) {
		try {
			Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate(templateName);
			return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
		} catch (IOException | TemplateException e) {
			return CommonConstant.BLANK;
		}
	}

	/**
	 * send HTML Message
	 * 
	 * @param to
	 * @param subject
	 * @param htmlBody
	 * @param multipartFile
	 * @return
	 */
	private boolean sendHtmlMessage(String to, String subject, String htmlBody, MultipartFile multipartFile) {
		if (!isMailServiceEnable)
			return false;
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(noReplyEmail);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlBody, true);
			if (!ObjectUtils.isEmpty(multipartFile))
				helper.addInline(multipartFile.getOriginalFilename(), multipartFile, multipartFile.getContentType());
			emailSender.send(message);
			return true;
		} catch (MailException | MessagingException e) {
			return false;
		}
	}

}
