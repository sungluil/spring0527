package com.spring.board.vo;

import org.springframework.web.multipart.MultipartFile;

public class BoardVo {
	
	private String 	boardType;
	private int 	boardNum;
	private String boardTitle;
	private String 	boardComment;
	private String 	creator;
	private String	modifier;
	private String boardHits;
	private String createTime;
	private int boardRef;
	private int boardLev;
	private int boardStep;
	private ComCodeVo comcodeVo;
	private UserInfoVo userinfoVo;
	private PostFileVo postFileVo;
	private MultipartFile[] file;
	private String fileRoot;
	private Long fileSize;
	
	
	

	
	public PostFileVo getPostFileVo() {
		return postFileVo;
	}
	public void setPostFileVo(PostFileVo postFileVo) {
		this.postFileVo = postFileVo;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public MultipartFile[] getFile() {
		return file;
	}
	public void setFile(MultipartFile[] file) {
		this.file = file;
	}
	public String getFileRoot() {
		return fileRoot;
	}
	public void setFileRoot(String fileRoot) {
		this.fileRoot = fileRoot;
	}
	public UserInfoVo getUserinfoVo() {
		return userinfoVo;
	}
	public void setUserinfoVo(UserInfoVo userinfoVo) {
		this.userinfoVo = userinfoVo;
	}
	public int getBoardRef() {
		return boardRef;
	}
	public void setBoardRef(int boardRef) {
		this.boardRef = boardRef;
	}
	public int getBoardLev() {
		return boardLev;
	}
	public void setBoardLev(int boardLev) {
		this.boardLev = boardLev;
	}
	public int getBoardStep() {
		return boardStep;
	}
	public void setBoardStep(int boardStep) {
		this.boardStep = boardStep;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getBoardHits() {
		return boardHits;
	}
	public void setBoardHits(String boardHits) {
		this.boardHits = boardHits;
	}
	public ComCodeVo getComcodeVo() {
		return comcodeVo;
	}
	public void setComcodeVo(ComCodeVo comcodeVo) {
		this.comcodeVo = comcodeVo;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardComment() {
		return boardComment;
	}
	public void setBoardComment(String boardComment) {
		this.boardComment = boardComment;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	
}
