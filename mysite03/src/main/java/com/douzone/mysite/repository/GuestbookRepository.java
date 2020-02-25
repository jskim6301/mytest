package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestbookVO;

@Repository
public class GuestbookRepository {
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
		List<GuestbookVO> result = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			
			String sql = "select no,name,contents,password,reg_date from guestbook order by no desc";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String contents = rs.getString(3);
				String password = rs.getString(4);
				String regDate =rs.getString(5);
				
				GuestbookVO vo = new GuestbookVO();
				vo.setNo(no);
				vo.setName(name);
				vo.setContents(contents);
				vo.setPassword(password);
				vo.setRegDate(regDate);				
				
				result.add(vo);
			}			
			
		}catch (SQLException e) {
			throw new GuestbookRepositoryException(e.getMessage());
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
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
