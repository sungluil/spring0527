package com.spring.one;

public class Log4jHelloWorld {
	
	public Log4jHelloWorld() {
		// TODO Auto-generated constructor stub
	}
	
	public String hello(String name) {
		System.out.println("HelloWorld클래스의 Main메소드 호출");
		String message = "hello"+name+"!!!";
		System.out.println("HelloWorld클래스의 Main메소드 종료");
		return message;
	}
}
