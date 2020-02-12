package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class writeformAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {		

		//접근 제어(Access Control List, ACL)
		
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute("authUser") != null) {
			
//			if( request.getParameter("no").isEmpty() ) {
//				System.out.println("없음");
//			}
			
			if( !(request.getParameter("no").isEmpty()) ) { //no값이 있는경우(view단에서 요청한 경우)
				String no = request.getParameter("no");
				Long No = Long.parseLong(no);
				
				request.setAttribute("No",No);
			}			
			
			WebUtil.forward("/WEB-INF/views/board/write.jsp", request, response);
			return;
		}
		WebUtil.redirect(request.getContextPath()+"/user?a=loginform", request, response);
		
	}

}
