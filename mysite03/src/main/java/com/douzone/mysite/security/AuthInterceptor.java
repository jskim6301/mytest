package com.douzone.mysite.security;

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
		//1. handler 종류 확인(디폴트 서블릿인지 controller에 대한 핸들러인지)
		if(!(handler instanceof HandlerMethod)) { //핸들러메소드가 아니라면 디폴트서블릿
			return true; // => defaultServletHandler가 처리하는 경우임
		}
		//형 변환
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class); //@Auth받아오기
		
		if(auth == null) { //@Auth가 없다면 Type에 붙어있는지도 확인
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}
		if(auth == null) { //Type,Method 둘다 @Auth가 적용이 안되어있음 -> 인증 필요없다
			return true; //요청한 값으로 그대로 진행
		}
		//이까지 오면@Auth가 있다는 뜻
		HttpSession session = request.getSession();
		if(session == null) { //세션이 있는지 체크
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		//세션이 존재한다면 유효한 유저인지 체크
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		String role = auth.value();
		if("USER".equals(role)) {//authUser의 role이 USER라면 요청한 값 그대로 진행
			return true;
		}else if("ADMIN".equals(authUser.getRole()) == false) {//authUser의 role이 USER가 아니면 무조건 ADMIN일텐데 만약 ADMIN이 아닐경우 막는다
			response.sendRedirect(request.getContextPath());
			return false;
		}
		return true;
	}

}

//return이  true라면 컨트롤러로 요청이 진행되고 false라면 컨트롤러로 진행되지 않고 바로 응답