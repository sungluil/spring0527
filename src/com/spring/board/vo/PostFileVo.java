package com.spring.board.vo;

/**
이름             널?       유형            
-------------- -------- ------------- 
FILE_ID        NOT NULL VARCHAR2(20)  
FILE_WRITER             VARCHAR2(20)  
FILE_NUM                NUMBER(11)    
FILE_GROUP              VARCHAR2(236) 
FILE_ORG_NAME           VARCHAR2(236) 
FILE_SAVE_NAME          VARCHAR2(236) 
FILE_TYPE               VARCHAR2(11)  
FILE_SIZE               NUMBER        
FILE_PATH               VARCHAR2(256) 
FILE_REG_DTTM           VARCHAR2(14)  
BOARD_NUM      NOT NULL NUMBER(11) 
**/

public class PostFileVo {
	private String fileId;
	private String fileWriter;
	private int fileNum;
	private String fileGroup;
	private String fileOrgName;
	private String fileSaveName;
	private String fileType;
	private Long fileSize;
	private String filePath;
	private String fileRegDttm;
	private int boardNum;
	private BoardVo boardVo;
	

	public PostFileVo() {
		// TODO Auto-generated constructor stub
	}
	
	public BoardVo getBoardVo() {
		return boardVo;
	}

	public void setBoardVo(BoardVo boardVo) {
		this.boardVo = boardVo;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileWriter() {
		return fileWriter;
	}

	public void setFileWriter(String fileWriter) {
		this.fileWriter = fileWriter;
	}

	public int getFileNum() {
		return fileNum;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}

	public String getFileGroup() {
		return fileGroup;
	}

	public void setFileGroup(String fileGroup) {
		this.fileGroup = fileGroup;
	}

	public String getFileOrgName() {
		return fileOrgName;
	}

	public void setFileOrgName(String fileOrgName) {
		this.fileOrgName = fileOrgName;
	}

	public String getFileSaveName() {
		return fileSaveName;
	}

	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileRegDttm() {
		return fileRegDttm;
	}

	public void setFileRegDttm(String fileRegDttm) {
		this.fileRegDttm = fileRegDttm;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	
	
}
