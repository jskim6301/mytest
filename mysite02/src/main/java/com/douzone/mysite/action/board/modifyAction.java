package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class modifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//접근 제어(Access Control List, ACL)
		
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute("authUser") != null) {
			BoardVO boardVO = new BoardVO();
			
			String no = request.getParameter("no");
			Long No = Long.parseLong(no);
			
			String title = request.getParameter("title");
			String contents = request.getParameter("contents");
			
			boardVO.setNo(No);
			boardVO.setTitle(title);
			boardVO.setContents(contents);
			
			
			new BoardRepository().update(boardVO);
			WebUtil.redirect(request.getContextPath()+"/board?num=1", request, response);
			return;
		}
		WebUtil.redirect(request.getContextPath(), request, response);

	}

}
