package com.saveit.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils 
{
	@Autowired
	//Class provided by Spring Boot to send the email
	private JavaMailSender mailSender;

	@Async
	public Boolean sendEmail(String to, String subject, String body) 
	{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		Boolean response = false;
		try 
		{
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setText(body,true);

			mailSender.send(mimeMessageHelper.getMimeMessage());
			response = true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
//			throw new SMTPException(e.getMessage());
			response = false;
		}
		return response;
	}

}
