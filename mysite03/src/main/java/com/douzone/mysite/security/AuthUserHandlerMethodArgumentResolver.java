package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.douzone.mysite.vo.UserVO;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {


	@Override
	public Object resolveArgument(//파라미터와 기타 정보를 받아서 실제 객체를 반환
			MethodParameter parameter,
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, 
			WebDataBinderFactory binderFactory) throws Exception {
		//1.파라미터에 @AuthUser가 붙어 있는지 타입이 UserVO 인지 확인
		if(supportsParameter(parameter)==false) {
			//내가 해석할 수 있는 파라미터가 아니다.
			return WebArgumentResolver.UNRESOLVED;
		}
		//@AuthUser가 붙어 있고 타입이 UserVO안 경우이다.
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpSession session = request.getSession(); 
		if(session == null) {
			return null;
		}
		return session.getAttribute("authUser");
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) { //Resolver가 적용 가능한지 검사하는 역할
		//@AuthUser가 붙어 있는지 확인
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		//@AuthUser가 안 붙어 있는 경우
		if(authUser == null) {
			return false;
		}
		// UserVO 타입이 아닌 경우
		if(parameter.getParameterType().equals(UserVO.class) == false ) {
			return false;
		}		
		return true;
	}
}
