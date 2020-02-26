package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVO;

@Repository
public class UserRepository {
	
//	private BasicDataSource basicDataSource;  => applicationContext에서 datasource가 여러개면 충돌이 일어나 오류가 난다. 그래서 DataSource로 받아서 Autowired를 통해 클래스(class)가 아닌 이름(id)지정방식으로 해준다.(1개면 오류안남)

//	@Autowired
//	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	

	public int insert(UserVO userVO) {
		
		return sqlSession.insert("user.insert",userVO);
	}

	public UserVO findByEmailAndPassword(UserVO vo) {
		
		return sqlSession.selectOne("user.findByEmailAndPassword",vo);
	}
	public UserVO findByEmailAndPassword(String email,String password) {
		Map<String,Object> map = new HashMap<>();
		map.put("e", email);
		map.put("p", password);
		
		return sqlSession.selectOne("user.findByEmailAndPassword2",map);
	}	

	public UserVO findByNo(Long no) {
		return sqlSession.selectOne("user.findByNo",no);	
	}

	
	public int update(UserVO userVO) {
		
		return sqlSession.update("user.update",userVO);	
	}
	
	/*
	private Connection getConnection() throws SQLException {
		Connection con = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			

			String url = "jdbc:mysql://192.168.1.115:3307/webdb";
			con =  DriverManager.getConnection(url,"webdb","webdb");
		} catch (ClassNotFoundException e) {
			throw new UserRepositoryException("드라이버 로딩 실패:"+ e);
		}
		return con;
	}*/
}
