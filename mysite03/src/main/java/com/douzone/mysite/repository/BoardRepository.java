package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVO;

@Repository
public class BoardRepository {
//	@Autowired
//	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	
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
	
	public Boolean initInsert(BoardVO boardVO) {
			
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

	public List<BoardVO> findAll(int displayPost,int postNum,String keyword){
		Map<String,Object> map = new HashMap<>();
		System.out.println("displayPost"+displayPost);
		System.out.println("postNum"+postNum);
		map.put("displayPost",displayPost);
		map.put("postNum",postNum);
		map.put("keyword",keyword);
		List<BoardVO> list = sqlSession.selectList("board.findAll",map);
		return list;
	}
	
	public List<BoardVO> findAll2(){
		List<BoardVO> result = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			con = getConnection();
			//select a.no,a.title,a.contents,a.Hit,a.reg_date,a.g_no,a.o_no,a.depth,a.user_no,b.name from board a,user b where a.user_no = b.no order by a.no desc;
			String sql = "select a.no,a.title,a.contents,a.hit,a.reg_date,a.g_no,a.o_no,a.depth,a.user_no,b.name from board a,user b where a.user_no = b.no order by a.g_no desc, a.o_no desc";
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
		return sqlSession.selectOne("board.findByNo",no);	
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

	public int insert(BoardVO boardVO) {

		return sqlSession.insert("board.insert",boardVO);
	}

	public int updateSequece(Integer gNo,Integer oNo) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("gNo",gNo);
		map.put("oNo",oNo);
		return sqlSession.update("board.updateOrderNo",map);
	}

	public int updateHit(Long no) {
		return sqlSession.update("board.updateHit",no);
		
	}

	public void delete(BoardVO boardVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();

			String sql = "update board set title = '작성자에 의해 삭제되었습니다.',contents=''  where no = ? and user_no = ?";
			pstmt = con.prepareStatement(sql);
			
			
			pstmt.setLong(1,boardVO.getNo());
			pstmt.setLong(2,boardVO.getUserNo());			
			
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

	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.totalCount",keyword);
	}

	public List<BoardVO> findByTitle(String kwd,int displayPost,int postNum) {
		List<BoardVO> result = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			
			
			// 
			String sql = "select a.no,b.name,a.title,a.contents,a.hit,a.reg_date from board a, user b where a.user_no = b.no and a.title like concat('%',?,'%') limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, kwd);
			pstmt.setInt(2, displayPost);
			pstmt.setInt(3, postNum);			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String userName = rs.getString(2);
				String title = rs.getString(3);
				String contents = rs.getString(4);
				Integer hit = rs.getInt(5);
				String regDate = rs.getString(6);
				
				
				BoardVO boardVO = new BoardVO();
				boardVO.setNo(no);
				boardVO.setUserName(userName);
				boardVO.setTitle(title);
				boardVO.setContents(contents);
				boardVO.setHit(hit);
				boardVO.setRegDate(regDate);
				
				
				result.add(boardVO);
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
		
		return result;				
	}

	public List<BoardVO> findByName(String kwd,int displayPost,int postNum) {
		
		List<BoardVO> result = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			
			
			// select a.no,b.name,a.title,a.contents from board a, user b where a,user_no = b.no where a.no = ?
			String sql = "select a.no,b.name,a.title,a.contents,a.hit,a.reg_date from board a, user b where a.user_no = b.no and b.name like concat('%',?,'%') limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, kwd);
			pstmt.setInt(2, displayPost);
			pstmt.setInt(3, postNum);			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String userName = rs.getString(2);
				String title = rs.getString(3);
				String contents = rs.getString(4);
				Integer hit = rs.getInt(5);
				String regDate = rs.getString(6);
						
				
				BoardVO boardVO = new BoardVO();
				boardVO.setNo(no);
				boardVO.setUserName(userName);
				boardVO.setTitle(title);
				boardVO.setContents(contents);
				boardVO.setHit(hit);
				boardVO.setRegDate(regDate);
				
				
				result.add(boardVO);
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
		
		return result;	
	}

	public int countTitle(String kwd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int count = 0;
		try {
			con = getConnection();

			String sql = "select count(*) from board a, user b where a.user_no = b.no and a.title like concat('%',?,'%')";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kwd);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);				
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
				
		return count;

	}

	public int countName(String kwd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int count = 0;
		try {
			con = getConnection();

			String sql = "select count(*) from board a, user b where a.user_no = b.no and b.name like concat('%',?,'%')";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, kwd);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);				
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
		return count;
	}
}
