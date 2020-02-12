package com.douzone.mysite.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.action.main.MainActionFactory;
import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;


public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

//	Map<String,Object> map = new HashMap<>(); //여기다가 하면 불안정, 서블릿에다가 지역변수 두지 말것 (스레드를 탈 수 있기 때문에 코드가 꼬일수 있음) 
	
	@Override
	public void init() throws ServletException {
		System.out.println("init() called");
		String configPath = getServletConfig().getInitParameter("config");
		System.out.println("init() called!!!!!!!!!! : "+configPath);
		
//		Map<String,Object> map = new HashMap<>();  //이곳에서 설정
//
//		this.getServletContext().setAttribute("cache", "");//전역context설정
		
		super.init();
	}
	
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		System.out.println("service() called");
		super.service(arg0, arg1);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet() called");
		
		
		
		String actionName = request.getParameter("a");
		
		ActionFactory af = new MainActionFactory();		
		Action action =  af.getAction(actionName);
		action.execute(request,response);
		
		
		
//		WebUtil.forward("/WEB-INF/views/main/index.jsp", request, response);
		
//		request.getRequestDispatcher("/WEB-INF/views/main/index.jsp").forward(request,response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost() called");
		doGet(request, response);
	}
	
	@Override
	public void destroy() {
		System.out.println("destroy() called");
		super.destroy();
	}

}
