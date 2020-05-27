package com.spring.board.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.spring.board.dao.BoardDao;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;
import com.spring.board.vo.PostFileVo;
import com.spring.board.vo.UserInfoVo;
import com.spring.common.CommandMap;

@Repository
public class BoardDaoImpl implements BoardDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public String selectTest() throws Exception {
		// TODO Auto-generated method stub
		
		String a = sqlSession.selectOne("board.boardList");
		
		return a;
	}
	/**
	 * 
	 * */
	@Override
	public List<BoardVo> selectBoardList(PageVo pageVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.boardList",pageVo);
	}
	
	@Override
	public int selectBoardCnt() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.boardTotal");
	}
	
	@Override
	public BoardVo selectBoard(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.boardView", boardVo);
	}
	
	@Override
	public int boardInsert(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.boardInsert", boardVo);
	}
	@Override
	public int boardUpdate(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("board.boardUpdate", boardVo);
	}

	@Override
	public int boardDelete(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.delete("board.boardDelete", boardVo);
	}
	@Override
	public List<BoardVo> searchBoardList(Map<String, Object> keyList) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.boardSearchList", keyList);
	}
	@Override
	public List<ComCodeVo> codeList(ComCodeVo comCodeVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.codeList", comCodeVo);
	}
	@Override
	public int userInsert(UserInfoVo userinfoVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.userInsert", userinfoVo);
	}
	@Override
	public UserInfoVo userinfoSelect(String userId) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.userinfoSelect", userId);
	}
	@Override
	public UserInfoVo idcheck(String userId) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.idcheck", userId);
	}
	@Override
	public UserInfoVo pwcheck(UserInfoVo userinfoVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.pwcheck", userinfoVo);
	}
	@Override
	public int pwCheckCount(UserInfoVo userinfoVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.pwCheckCount", userinfoVo);
	}
	@Override
	public BoardVo updateReadHits(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.updateReadHits",boardVo);
	}
	@Override
	public List<BoardVo> selectPaperBoardList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.boardList", map);
	}
	@Override
	public int boardInsertRef(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.boardInsertRef", boardVo);
	}
	@Override
	public int boardUpdateRef(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("board.boardUpdateRef", boardVo);
	}
	@Override
	public UserInfoVo checkUserInfoCookie(String sessionId) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.checkUserInfoCookie", sessionId);
	}
	@Override
	public void KeepLogin(Map<String, Object> map) throws Exception {
		sqlSession.update("board.keepLogin", map);
		
	}
	@Override
	public Map<String, Object> userinfoSelect(Map<String, Object> map)
			throws Exception {
		return sqlSession.selectOne("board.userinfoSelect", map);
	}
	@Override
	public UserInfoVo getPassword(UserInfoVo userinfoVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.getPassword", userinfoVo);
	}
	@Override
	public int boardInsertAll(List<BoardVo> list) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.boardInsertAll", list);
	}
	@Override
	public int postFileInsert(PostFileVo postFileVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.postFileInsert", postFileVo);
	}
	@Override
	public int postFileInsertAll(List<PostFileVo> fileList) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.postFileInsertAll", fileList);
	}
	@Override
	public List<PostFileVo> selectFile(PostFileVo postFileVo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.fileView", postFileVo);
	}
	@Override
	public int selectFileCnt() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.fileTotal");
	}
	@Override
	public Map<String, Object> fileDownlad(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.fileDownload", map);
	}





	
	
}
