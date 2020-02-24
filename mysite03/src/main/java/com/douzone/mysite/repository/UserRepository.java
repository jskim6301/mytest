package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVO;

@Repository
public class UserRepository {
	private Connection getConnection() throws SQLException {
		Connection con = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			

			String url = "jdbc:mysql://192.168.1.115:3307/webdb";
			con =  DriverManager.getConnection(url,"webdb","webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:"+ e);
		}
		return con;
	}
	
	
	public int insert(UserVO userVO) {
		
		Boolean result = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			con = getConnection();
			//insert into user values(null,'홍길동','안녕하세요','1234',now())
			String sql = "insert into user values(null,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, userVO.getName());
			pstmt.setString(2, userVO.getEmail());
			pstmt.setString(3, userVO.getPassword());
			pstmt.setString(4, userVO.getGender());

			
			count = pstmt.executeUpdate();
//			result = count == 1;
			count = 1;
			
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
	return count;
	}

	public UserVO findByEmailAndPassword(UserVO vo) {
		UserVO userVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			
			String sql = "select no,name from user where email = ? and password = ?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				
				
				userVO = new UserVO();
				userVO.setNo(no);
				userVO.setName(name);
								
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
		
		return userVO;
	}

	public UserVO findByNo(Long no) {
		
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			UserVO userVO = new UserVO();
			try {
				con = getConnection();
				
				
				
				String sql = "select no,name,email,password,gender,join_date from user where no = ?";
				pstmt=con.prepareStatement(sql);
				pstmt.setLong(1, no);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					Long newNo = rs.getLong(1);
					String name = rs.getString(2);
					String email = rs.getString(3);
					String password = rs.getString(4);
					String gender = rs.getString(5);
					String joinDate = rs.getString(6);
					
					userVO.setNo(newNo);
					userVO.setName(name);
					userVO.setEmail(email);
					userVO.setPassword(password);
					userVO.setGender(gender);
					userVO.setJoinDate(joinDate);
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
			
			return userVO;		
	}

	public void update(UserVO userVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();

			String sql = "update user set name =?, password=?,gender=?,join_date = now() where no = ?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1,userVO.getName());
			pstmt.setString(2,userVO.getPassword());
			pstmt.setString(3,userVO.getGender());
			pstmt.setLong(4, userVO.getNo());
			
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
