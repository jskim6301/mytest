package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.vo.UserVO;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//1. handler 종류 확인 (디폴트 서블릿이거나 controller에 대한 핸들러메소드이거나 둘 중 하나) (어노테이션이 붙어있는가 아닌가 확인하기 위함)
		if(handler instanceof HandlerMethod == false) { //핸들러메소드가 아니면 디폴트 서블릿 
			return true; //DefaultServletHandler가 처리하는 경우 (보통 assets의 정적자원 접근)  이미지url로 들어올때 assets으로 들어온것
		}
		
		//2. casting  => 핸들러메소드로 캐스팅
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3.Method의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//4.Method에 @Auth가 없으면 Type에 붙어 있는지 확인한다(과제)
		if(auth == null) {
			return true;
		}
		
		//5.@Auth가 있다 => 인증 여부 확인
		HttpSession session = request.getSession(); //HttpSession session = request.getSession(false);
		if(session == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		UserVO authUser = (UserVO)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;			
		}
		
		
		
		
		//6.role 가져오기
//		Auth.Role role = auth.role();
		String role = auth.value();
		System.out.println("role:"+role);
		
		
		//인증확인 되었으므로 핸들러 메소드 실행
		return true;
	}

}

/*
Object handler => 핸들러 매핑을 담는 정보(메소드에 대한 정보  어노테이션이 어떤것이 달려있고 Auth가 달려있는지 아닌지 확인 가능한 객체)

핸들러 매핑,디폴트 핸들러에 대한 처리를 여기서 가능

HandlerMethod => controller 에 대한 핸들러

 이미지 URL로 들어올때는 디폴트 서블릿 핸들러로 들어온다
*/