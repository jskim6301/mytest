package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVO;

@Repository
public class BoardRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.totalCount",keyword);
	}

	public List<BoardVO> findAll(int displayPost, int postnum, String keyword) {
		Map<String,Object> map = new HashMap<>();
		map.put("displayPost",displayPost);
		map.put("postnum", postnum);
		map.put("keyword", keyword);
		List<BoardVO> list = sqlSession.selectList("board.findAll",map);
		return list;
	}

	public BoardVO findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo",no);
	}

	public int updateHit(Long no) {
		return sqlSession.update("board.updateHit",no);		
	}

	public int insert(BoardVO boardVO) {
		return sqlSession.insert("board.insert",boardVO);
	}

	public int updateSequence(Integer gNo, Integer oNo) {
		Map<String,Object> map = new HashMap<>();
		map.put("gNo", gNo);
		map.put("oNo",oNo);
		return sqlSession.update("board.updateOrderNo",map);
	}

	public int delete(Long no, Long userNo) {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("no", no);
		map.put("userNo", userNo);
		return sqlSession.delete("board.delete",map);
	}

	public BoardVO findByNoAndUserNo(Long no, Long userNo) {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("no", no);
		map.put("userNo", userNo);
		return sqlSession.selectOne("board.findByNoAndUserNo",map);
	}

	public int update(BoardVO boardVO) {
		return sqlSession.update("board.update",boardVO);
	}
}
