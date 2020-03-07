package com.douzone.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVO;

@Repository
public class UserRepository {
	
	@Autowired
	private SqlSession sqlSession;
	public int insert(UserVO vo) {
		return sqlSession.insert("user.insert",vo);
	}
	public UserVO findByEmailAndPassword(UserVO vo) {
		return sqlSession.selectOne("user.findByEmailAndPassword",vo);
	}
	public UserVO findByNo(Long no) {
		return sqlSession.selectOne("user.findByNo",no);
	}
	public int update(UserVO userVO) {
		return sqlSession.update("user.update",userVO);
	}
	
}
