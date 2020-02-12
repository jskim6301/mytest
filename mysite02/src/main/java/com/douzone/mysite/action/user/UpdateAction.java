package com.douzone.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//접근 제어(Access Control List, ACL)
		HttpSession session = request.getSession();
		if(session == null) {
			WebUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		UserVO authUser = (UserVO)session.getAttribute("authUser");
		
		UserVO userVO = new UserVO();
		
		Long no = authUser.getNo();
		userVO.setNo(no);
		
		String name = request.getParameter("name");
		userVO.setName(name);
		
		if("".equals(request.getParameter("password")) ) {
			// 1.패스워드가 빈값인 경우 => 패스워드 유지
			String password2 = authUser.getPassword();
			userVO.setPassword(password2);
		}else {
			// 2.패스워드 값이 들어간 경우
			String password =request.getParameter("password");
			userVO.setPassword(password);
		}		
		
		String gender = request.getParameter("gender");
		userVO.setGender(gender);
		
		new UserRepository().update(userVO);
		
		WebUtil.redirect(request.getContextPath(), request, response);
	}

}
