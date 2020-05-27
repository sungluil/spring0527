package com.spring.one;

public class MessagePrint {
	private MessageObject messageObject;

	public MessageObject getMessageObject() {
		return messageObject;
	}

	public void setMessageObject(MessageObject messageObject) {
		this.messageObject = messageObject;
	}
	
	public void MessagePrint() {
		String message = messageObject.getMessage();
		System.out.println("message = "+message);
	}
	
	
	
	
	
}
