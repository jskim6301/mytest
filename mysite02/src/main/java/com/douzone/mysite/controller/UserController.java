package com.douzone.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.action.user.UserActionFactory;
import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;
import com.douzone.web.util.WebUtil;


public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		String action = request.getParameter("a");

		
		
		
		String actionName = request.getParameter("a");
		
//		ActionFactory af = new UserActionFactory();		
//		Action action =  af.getAction(actionName);
//		action.execute(request,response);		

		Action action = new UserActionFactory().getAction(actionName);		
		action.execute(request,response);		
		
		
//		String actionName = request.getParameter("a");
//		
//		ActionFactory af = new UserActionFactory();		
//		Action action =  af.getAction(actionName);
//		action.execute(request,response);
		
		/*
		if("joinform".equals(action)) {
//			WebUtil.forward("/WEB-INF/views/user/joinform.jsp", request, response); => com.douzone.mysite.actuon.user에서 JoinformAction으로 이동
//			request.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp").forward(request,response);
		}else if("join".equals(action)) {
			
		}else if("joinsuccess".equals(action)) {
//			WebUtil.forward("/WEB-INF/views/user/joinsuccess.jsp", request, response);
//			request.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp").forward(request,response);
		}else if("loginform".equals(action)) {
//			WebUtil.forward("/WEB-INF/views/user/loginform.jsp", request, response);
//			request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp").forward(request,response);
		}else if("login".equals(action)) {
			
		}else {
			WebUtil.redirect(request.getContextPath(),request,response);
//			response.sendRedirect(request.getContextPath());
			
		}
		*/
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
