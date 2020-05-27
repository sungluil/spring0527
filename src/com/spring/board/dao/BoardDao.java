package com.spring.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;
import com.spring.board.vo.PostFileVo;
import com.spring.board.vo.UserInfoVo;
import com.spring.common.CommandMap;

public interface BoardDao {

	public String selectTest() throws Exception;

	public List<BoardVo> selectBoardList(PageVo pageVo) throws Exception;
	
	public List<BoardVo> selectPaperBoardList(Map<String, Object> map) throws Exception;

	public BoardVo selectBoard(BoardVo boardVo) throws Exception;
	//파일조회
	public List<PostFileVo> selectFile(PostFileVo postFileVo) throws Exception;

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
    //비번체크
    public UserInfoVo pwcheck(UserInfoVo userinfoVo) throws Exception;
    
    public int pwCheckCount(UserInfoVo userinfoVo) throws Exception;
    
    //조회수
    public BoardVo updateReadHits(BoardVo boardVo) throws Exception;
    
    public UserInfoVo checkUserInfoCookie(String sessionId) throws Exception;
    
    public void KeepLogin(Map<String, Object> map) throws Exception;
    
    public Map<String, Object> userinfoSelect(Map<String, Object> map) throws Exception;
    
    public UserInfoVo getPassword(UserInfoVo userinfoVo) throws Exception;
    
    //답글
    public int boardInsertRef(BoardVo boardVo) throws Exception;
    public int boardUpdateRef(BoardVo boardVo) throws Exception;
    
    //메일발송
    //public void sendMail(String content) throws Exception;
    
    public Map<String, Object> fileDownlad(Map<String, Object> map) throws Exception;
    //public PostFileVo fileDownload(String fileNum) throws Exception;
    
}
