package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class searchAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<BoardVO> list = null;
		BoardVO boardVO;
		String option = request.getParameter("option");
		String kwd;
		
		if( !(request.getParameter("kwd").isEmpty()) ) {
			kwd = request.getParameter("kwd");//글자를 받아옴
			if(option.equals("title")) {
				list = new BoardRepository().findByContents(kwd);
				
				
			}else if(option.equals("name")) {
				list = new BoardRepository().findByName(kwd);
				
				
				
			}
			request.setAttribute("list", list);
			WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
			
		}else {
			WebUtil.redirect(request.getContextPath()+"/board?num=1", request, response);
			return;
		}		

	}

}
