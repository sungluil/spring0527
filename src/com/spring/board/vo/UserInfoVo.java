package com.spring.board.vo;

/*
�̸�            ��?       ����            
------------- -------- ------------- 
USER_ID       NOT NULL VARCHAR2(15)  
USER_PW                VARCHAR2(16)  
USER_NAME              VARCHAR2(15)  
USER_PHONE1            VARCHAR2(3)   
USER_PHONE2            VARCHAR2(4)   
USER_PHONE3            VARCHAR2(4)   
USER_ADDR1             VARCHAR2(8)   
USER_ADDR2             VARCHAR2(150) 
USER_COMPANY           VARCHAR2(60)  
CREATOR                VARCHAR2(15)  
CREATE_TIME            VARCHAR2(14)  
MODIFIER               VARCHAR2(15)  
MODIFIED_TIME          VARCHAR2(14)
*/ 
public class UserInfoVo {
	private String userId;
	private String userPw;
	private String userName;
	private String userPhone1;
	private String userPhone2;
	private String userPhone3;
	private String userAddr1;
	private String userAddr2;
	private String userCompany;
	private String createTime;
	private String sessionKey;
	private String sessionLimit;
	
	public UserInfoVo() {
		// TODO Auto-generated constructor stub
	}
	
	

	public String getSessionKey() {
		return sessionKey;
	}



	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}



	public String getSessionLimit() {
		return sessionLimit;
	}



	public void setSessionLimit(String sessionLimit) {
		this.sessionLimit = sessionLimit;
	}



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone1() {
		return userPhone1;
	}

	public void setUserPhone1(String userPhone1) {
		this.userPhone1 = userPhone1;
	}

	public String getUserPhone2() {
		return userPhone2;
	}

	public void setUserPhone2(String userPhone2) {
		this.userPhone2 = userPhone2;
	}

	public String getUserPhone3() {
		return userPhone3;
	}

	public void setUserPhone3(String userPhone3) {
		this.userPhone3 = userPhone3;
	}

	public String getUserAddr1() {
		return userAddr1;
	}

	public void setUserAddr1(String userAddr1) {
		this.userAddr1 = userAddr1;
	}

	public String getUserAddr2() {
		return userAddr2;
	}

	public void setUserAddr2(String userAddr2) {
		this.userAddr2 = userAddr2;
	}

	public String getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	


	
	
}
