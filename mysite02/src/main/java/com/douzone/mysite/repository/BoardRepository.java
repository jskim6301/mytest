package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVO;
import com.douzone.mysite.vo.UserVO;

public class BoardRepository {
	private Connection getConnection() throws SQLException {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			

			String url = "jdbc:mysql://127.0.0.1:3306/webdb";
			con =  DriverManager.getConnection(url,"webdb","webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:"+ e);
		}
		return con;
	}
	
	public Boolean insert(BoardVO boardVO) {
			
			Boolean result = false;
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = getConnection();
				//INSERT INTO board VALUES(NULL,'ㅎㅇ','ㅎㅇㅎㅇ',4,NOW(),(SELECT IFNULL(MAX(b.g_no) + 1,0) FROM board b),1,0,1)
				String sql = "insert into board values(null,?,?,0,now(),(SELECT IFNULL(MAX(b.g_no) + 1,0) FROM board b),1,0,?)";
				pstmt = con.prepareStatement(sql);
	
				pstmt.setString(1, boardVO.getTitle());
				pstmt.setString(2, boardVO.getContents());
				pstmt.setLong(3, boardVO.getUserNo());
	
				
				int count = pstmt.executeUpdate();
				
				result = count == 1;
				
			}catch (SQLException e) {
				System.out.println("error" + e);
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

	public List<BoardVO> findAll(){
		List<BoardVO> result = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			con = getConnection();
			//select a.no,a.title,a.contents,a.Hit,a.reg_date,a.g_no,a.o_no,a.depth,a.user_no,b.name from board a,user b where a.user_no = b.no order by a.no desc;
			String sql = "select a.no,a.title,a.contents,a.hit,a.reg_date,a.g_no,a.o_no,a.depth,a.user_no,b.name from board a,user b where a.user_no = b.no order by a.no desc";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Integer hit = rs.getInt(4);
				String regDate = rs.getString(5);
				Integer gNo = rs.getInt(6);
				Integer oNo = rs.getInt(7);
				Integer depth = rs.getInt(8);
				Long userNo = rs.getLong(9);
				String userName = rs.getString(10);
				
				
				BoardVO vo = new BoardVO();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				
				result.add(vo);
			}			
			
		}catch (SQLException e) {
			System.out.println("error" + e);
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

	public BoardVO findByNo(Long no) {
		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO boardVO = new BoardVO();
		try {
			con = getConnection();
			
			
			// select a.no,b.name,a.title,a.contents from board a, user b where a,user_no = b.no where a.no = ?
			String sql = "select a.no,b.name,a.title,a.contents from board a, user b where a.user_no = b.no and a.no = ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long newNo = rs.getLong(1);
				String userName = rs.getString(2);
				String title = rs.getString(3);
				String contents = rs.getString(4);
				
				
				boardVO.setNo(newNo);
				boardVO.setUserName(userName);
				boardVO.setTitle(title);
				boardVO.setContents(contents);
				
			}		

			
		}catch (SQLException e) {
			System.out.println("error" + e);
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
		
		return boardVO;		
	}
	
	public void update(BoardVO boardVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();

			String sql = "update board set title =?, contents=?, reg_date = now() where no = ?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1,boardVO.getTitle());
			pstmt.setString(2,boardVO.getContents());
			pstmt.setLong(3, boardVO.getNo());
			
			pstmt.executeUpdate();			
			
		}catch (SQLException e) {
			System.out.println("error" + e);
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
		
		
	}	
	
}
