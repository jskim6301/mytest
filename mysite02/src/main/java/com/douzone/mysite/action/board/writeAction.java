package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVO;
import com.douzone.mysite.vo.UserVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class writeAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//접근 제어(Access Control List, ACL)
		
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute("authUser") != null) {
			
			BoardVO boardVO = new BoardVO();
			
			UserVO authUser = (UserVO)session.getAttribute("authUser");
			Long no =  authUser.getNo();
			
			String title = request.getParameter("title");
			String contents = request.getParameter("contents");
			
			boardVO.setTitle(title);
			boardVO.setContents(contents);
			boardVO.setUserNo(no);
			
			
			new BoardRepository().insert(boardVO);
			WebUtil.redirect(request.getContextPath()+"/board", request, response);
			return;
		}
		WebUtil.redirect(request.getContextPath()+"/user?a=loginform", request, response);
		
		
		

	}

}


