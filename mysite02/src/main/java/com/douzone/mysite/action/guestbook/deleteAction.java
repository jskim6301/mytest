package com.douzone.mysite.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class deleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");
		String password = request.getParameter("password");
		Long No = Long.parseLong(no);
		
		GuestbookVO vo = new GuestbookVO();
		vo.setNo(No);
		vo.setPassword(password);
		
		new GuestbookRepository().delete(vo);
		
		WebUtil.redirect(request.getContextPath(),request,response);

	}

}
