package com.spring.aop;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

//이메일을 전송하는 기능을 제공하는 메소드가 선언된 클래스
public class EmailSendBean {
	//이메일 전송을 위한 JavaMailSender 인스턴스를 저장하기 위한 필드
	private JavaMailSender mailSender;

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	//이메일을 전송하는 메소드
	// => 받는 사람의 이메일 주소, 제목, 내용을 매개변수에 전달받아 저장
	// => 받는 사람의 이메일 주소 반환
	public String sendEmail(String email, String subject, String content) throws MessagingException {
		//JavaMailSender.createMimeMessage() : MimeMessage 인스턴스를 생성하여 반환하는 메소드
		// => MimeMessage : 이메일 전송에 필요한 정보를 저장하기 위한 클래스
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper messageHelper=new MimeMessageHelper(message, true);
		
		try {
			//MimeMessage.setSubject(String subject) : 이메일 제목을 변경하는 메소드
			//message.setSubject(subject);
			messageHelper.setSubject(subject);
			//MimeMessage.setText(String text) : 이메일 내용을 변경하는 메소드
			//message.setText(content);
			messageHelper.setText(content, true);
			//MimeMessage.setRecipients(RecipientType type, InternetAddress email)
			// => 받는 사람에 대한 정보를 변경하는 메소드 - 받는 사람의 이메일 주소 변경
			//message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email));
			messageHelper.setTo(email);
			
			FileSystemResource file = new FileSystemResource(new File("D:/sample.png"));
			messageHelper.addAttachment("Google.png", file);
			
			//JavaMailSender.send(MimeMessage message) : 이메일을 전송하는 메소드
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return email;
	}
}







