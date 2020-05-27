package com.spring.one;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jHelloWorldMain {
	private final static Logger logger = LoggerFactory.getLogger(Log4jHelloWorldMain.class);
	
	public static void main(String[] args) {
		logger.info("start");
		Log4jHelloWorld hw = new Log4jHelloWorld();
		String message = hw.hello("홍길동");
		System.out.println("message = "+message);
		logger.info("end");
	}
}
