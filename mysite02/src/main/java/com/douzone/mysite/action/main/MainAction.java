package com.douzone.mysite.action.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class MainAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int visitCount = 0;
		
		
		//쿠키읽기
		Cookie[] cookies = request.getCookies(); //Request Headers에서
		if(cookies != null && cookies.length>0) { //Cookie:JSESSIONID=682C535547D09CDECD9EF884DC195FB8; visitCount=0
													// JSESSIONID는 쿠키id가 같은 애들을 모아놓은 것들
			for(Cookie cookie : cookies) {
				if("visitCount".equals(cookie.getName())) {
					visitCount = Integer.parseInt(cookie.getValue());
				}
			}
		}
		
		//쿠키 쓰기(굽기)  --처음 시작시 쿠키읽기보다 먼저 실행
		visitCount++;
		Cookie cookie = new Cookie("visitCount",String.valueOf(visitCount));
		cookie.setMaxAge(24*60*60); // 1 day
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
		
		WebUtil.forward("/WEB-INF/views/main/index.jsp", request, response);
	}

}
