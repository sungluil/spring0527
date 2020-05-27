package com.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.board.vo.Email;
import com.spring.common.EmailSender;

public class EmailSendMain {
	public static void main(String[] args) throws Exception {
//		ApplicationContext context=new ClassPathXmlApplicationContext("email.xml");
//		System.out.println("===================================================================================");
//		EmailSendBean bean=context.getBean("emailSendBean", EmailSendBean.class);
//		bean.sendEmail("yabukio@naver.com", "JavaMail 기능을 이용한 메일 전송", "잘 도착했는지 답장 부탁드립니다.");
//		System.out.println("===================================================================================");
//		((ClassPathXmlApplicationContext)context).close();
		
		
		Email email = new Email();
		email.setContent("내용");
		email.setSubject("제목");
		email.setReceiver("yabukio@naver.com");
		ApplicationContext context=new ClassPathXmlApplicationContext("email.xml");
		System.out.println("===================================================================================");
		EmailSender bean=context.getBean("emailSender", EmailSender.class);
		bean.SendEmail(email);
		System.out.println("===================================================================================");
		((ClassPathXmlApplicationContext)context).close();
	}
}

