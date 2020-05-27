package com.spring.board.service;

import java.util.List;
import java.util.Map;

import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;
import com.spring.board.vo.PostFileVo;
import com.spring.board.vo.UserInfoVo;

public interface boardService {

	public String selectTest() throws Exception;

	public List<BoardVo> SelectBoardList(PageVo pageVo) throws Exception;
	
	public List<BoardVo> selectPaperBoardList(Map<String, Object> map) throws Exception;

	public BoardVo selectBoard(String boardType, int boardNum) throws Exception;
	
	//파일검색
	public List<PostFileVo> selectFile(String fileGroup, int boardNum) throws Exception;

	public int selectBoardCnt() throws Exception;

	public int selectFileCnt() throws Exception;
	//단일행
	public int boardInsert(BoardVo boardVo) throws Exception;
	//다중행
	public int boardInsertAll(List<BoardVo> list) throws Exception;
	
	public int postFileInsert(PostFileVo postFileVo) throws Exception;
	
	public int postFileInsertAll(List<PostFileVo> fileList) throws Exception;
	
	public int boardUpdate(BoardVo boardVo) throws Exception;
	
	public int boardDelete(BoardVo boardVo) throws Exception;
	
	public List<BoardVo> searchBoardList(Map<String, Object> keyList) throws Exception;
	
	public List<ComCodeVo> codeList(ComCodeVo comCodeVo) throws Exception;
	
	public int userInsert(UserInfoVo userinfoVo) throws Exception;
	
	public UserInfoVo userinfoSelect(String userId) throws Exception;
	
	public UserInfoVo idcheck(String userId) throws Exception;
	
	public UserInfoVo pwcheck(String userId, String userPw) throws Exception;
	
	public int pwCheckCount(UserInfoVo userinfoVo) throws Exception;
	
	public BoardVo updateReadHits(String boardType, int boardNum) throws Exception;
	
	//자동로그인 서비스 호출
	public UserInfoVo checkUserInfoCookie(String sessionId) throws Exception;
	
	public void KeepLogin(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> userinfoSelect(Map<String, Object> map) throws Exception;
	
	public UserInfoVo getPassword(String userId, String userCompany) throws Exception;
	
	//답글
    public int boardInsertRef(BoardVo boardVo) throws Exception;
    public int boardUpdateRef(BoardVo boardVo) throws Exception;
    
    public Map<String, Object> fileDownlad(Map<String, Object> map) throws Exception;

}
