package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestbookVO;

@Repository
public class GuestbookRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	private Connection getConnection() throws SQLException {
		Connection con = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			

			String url = "jdbc:mysql://192.168.1.115:3307/webdb";
			con =  DriverManager.getConnection(url,"webdb","webdb");
		} catch (ClassNotFoundException e) {
			throw new GuestbookRepositoryException("드라이버 로딩 실패:"+ e);
		}
		return con;
	}
	

	public int insert(GuestbookVO guestbookVO) {
		
		Boolean result = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			con = getConnection();
			//insert into guestbook values(null,'홍길동','안녕하세요','1234',now())
			String sql = "insert into guestbook values(null,?,?,?,now())";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, guestbookVO.getName());
			pstmt.setString(2, guestbookVO.getContents());
			pstmt.setString(3, guestbookVO.getPassword());

			
			count = pstmt.executeUpdate();
			
			count = 1;
//			result = count == 1;
			
		}catch (SQLException e) {
			throw new GuestbookRepositoryException(e.getMessage());
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();	
				}				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	return count;
	}
	
	
	public List<GuestbookVO> findAll(){
		List<GuestbookVO> list = sqlSession.selectList("guestbook.findAll");
		return list;
	}
	
	public boolean delete(Long no, String password) {

		Boolean result = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();	

			String sql = "delete from guestbook where no = ? and password = ?";
			pstmt = con.prepareStatement(sql);

			pstmt.setLong(1, no);
			pstmt.setString(2, password);

			int count = pstmt.executeUpdate();

			result = count == 1;

		}catch (SQLException e) {
			throw new GuestbookRepositoryException(e.getMessage());
		}finally {

			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();	
				}				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
