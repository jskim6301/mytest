package com.douzone.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextLoadListener implements ServletContextListener {
    
	public void contextInitialized(ServletContextEvent servletContextEvent)  {
		ServletContext context =  servletContextEvent.getServletContext();
//		context.setAttribute(name, object);  //request에서 ServletContext를 구할 수 있음. 톰캣 종료시까지 계속 유지 가능
		
		String contextConfigLocation =  context.getInitParameter("contextConfigLocation");
		
    	System.out.println("Application Starts..."+contextConfigLocation);
    }
	
    public void contextDestroyed(ServletContextEvent arg0)  { 
    
    }


	
}
