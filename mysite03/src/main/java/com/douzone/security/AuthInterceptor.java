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
		//if(auth == null) {
			//auth = <- 과제
		//}
		
		//5. Type이나 Method 둘 다 @Auth가 적용이 안되어 있는 경우,
		if(auth == null) {
			return true; //인증을 안받아도 된다는 뜻
		}
		
		//6.@Auth가 있다 => 인증 여부 확인  (Authentification)
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
		
		//7.권한(Authorization) 체크를 위해서 @Auth의 role 가져오기  ("USER","ADMIN") 
//		Auth.Role role = auth.role();
		String role = auth.value();
		System.out.println("role:"+role);
		
		 //     USER=> 일반 유저 로그인 후 주소창에 /user로 들어간 경우 
		 //     ADMIN=> 일반 유저 로그인 후 주소창에 /admin으로 들어간 경우
		if("USER".equals(role)) { //8. @Auth의 role이 "USER"인 경우에는 authUser의 role이 "USER"이든 "ADMIN"이든 상관이 없음.
			return true;
		}else if("ADMIN".equals(role) == false) { //9. @Auth의 role이 "ADMIN"인 경우에는 반드시 authUser의 role이 "ADMIN" 여야 한다.
			response.sendRedirect(request.getContextPath());
			return false; 
		}
		
		

		//@Auth의 role => "ADMIN"
		//authUSer의 role => "ADMIN"
		//관리자 권한이 확인
		
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