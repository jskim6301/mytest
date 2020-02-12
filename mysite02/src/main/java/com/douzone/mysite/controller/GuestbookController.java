package com.douzone.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.action.guestbook.GuestbookActionFactory;
import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVO;
import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;
import com.douzone.web.util.WebUtil;



public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		String action = request.getParameter("a");
		
	
		
		String actionName = request.getParameter("a");
		if(actionName==null) {
			actionName = "aaa";
		}		
		
		ActionFactory af = new GuestbookActionFactory();		
		Action action =  af.getAction(actionName);
		action.execute(request,response);
/*		
		if("deleteform".equals(action)) {
			String no = request.getParameter("no");

			request.setAttribute("no", no);
			WebUtil.forward("/WEB-INF/views/guestbook/deleteform.jsp", request, response);
			
			
			
//			RequestDispatcher rd =  request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
//			rd.forward(request, response);			
		}else if("delete".equals(action)) {
			
			String no = request.getParameter("no");
			String password = request.getParameter("password");
			Long No = Long.parseLong(no);
			
			GuestbookVO vo = new GuestbookVO();
			vo.setNo(No);
			vo.setPassword(password);
			
			new GuestbookRepository().delete(vo);
			
			WebUtil.redirect(request.getContextPath(),request,response);
//			response.sendRedirect(request.getContextPath()+"/");			
		}else if("insert".equals(action)) {
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String contents = request.getParameter("content");
			 
			
			if(!name.isEmpty() || !password.isEmpty() || !contents.isEmpty()){
				GuestbookVO vo = new GuestbookVO();
			
				vo.setName(name);
				vo.setPassword(password);
				vo.setContents(contents);
				
				
				new GuestbookRepository().insert(vo);	
			}
			
			WebUtil.redirect(request.getContextPath()+"/guestbook",request,response);
//			response.sendRedirect(request.getContextPath()+"/guestbook");
		}else {
			//default 요청 처리(list)
			List<GuestbookVO> list = new GuestbookRepository().findAll();
			request.setAttribute("list", list);
			
			
			WebUtil.forward("/WEB-INF/views/guestbook/list.jsp", request, response);
			
//			 RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp");
//			 rd.forward(request, response);
						
		}
*/
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
