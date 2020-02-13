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

public class deleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute("authUser") != null) {
			
			BoardVO boardVO = new BoardVO();
			String no = request.getParameter("no"); //페이지 no
			Long No = Long.parseLong(no);
			
			UserVO authUser = (UserVO)session.getAttribute("authUser");
			Long userNo = authUser.getNo();
			
			boardVO.setNo(No);
			boardVO.setUserNo(userNo);
			
			new BoardRepository().delete(boardVO);
			
			WebUtil.redirect(request.getContextPath()+"/board", request, response);	
			return;
		}
						
		WebUtil.redirect(request.getContextPath()+"/user?a=loginform", request, response);
		
	}

}
