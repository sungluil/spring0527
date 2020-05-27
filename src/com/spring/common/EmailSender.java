package com.spring.common;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import com.spring.board.vo.Email;


public class EmailSender {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void SendEmail(Email email) throws Exception {
		//이메일 객체
		MimeMessage msg = mailSender.createMimeMessage();
		try {
			msg.setSubject(email.getSubject());//제목
			msg.setText(email.getContent());//내용
			//받는사람
			msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email.getReceiver()));
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		try {
			//이메일 보내
			mailSender.send(msg);
		} catch (MailException e) {
			System.out.println("MailException 발생");
			e.printStackTrace();
		}
	}
}
