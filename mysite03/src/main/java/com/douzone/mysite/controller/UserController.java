package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVO;
import com.douzone.security.Auth;

//@Auth
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)	
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)	
	public String join(UserVO vo) {
		System.out.println(vo);
		userService.join(vo);
		System.out.println(vo);//프라이머리 키만 가능
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
//	@RequestMapping(value="/login",method=RequestMethod.POST)
//	public String login(HttpSession session, @ModelAttribute UserVO vo) {
//		
//		UserVO authUser =  userService.getUser(vo);
//		if(authUser == null) {
//			return "user/login";
//		}
//		
//		session.setAttribute("authUser", authUser);
//		return "redirect:/";
//	}
	
//	@RequestMapping(value="/logout")
//	public String logout(HttpSession session) {
//		/////////////////////////접근제어/////////////////////////////////////
//		UserVO authUser = (UserVO)session.getAttribute("authUser");
//		if(authUser == null) {
//			return "redirect:/";
//		}		
//		//////////////////////////////////////////////////////////////
//		session.removeAttribute("authUser");
//		session.invalidate();
//		return "redirect:/";
//	}
	
//	@Auth(value ="user", test=true)
//	@Auth(value ="user")
//	@Auth(role=Auth.Role.USER) //생략가능(role=Auth.Role.USER)
	@Auth
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(HttpSession session,Model model) {
//	public String update(@AuthUser UserVO authUser,Model model) {	
		//접근제어 처리완료
		
		UserVO authUser = (UserVO) session.getAttribute("authUser");

		Long no = authUser.getNo();
		UserVO vo = userService.getUser(no);
		
		model.addAttribute("userVO",vo);
		return "user/update";
		
	}	
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(HttpSession session,UserVO userVO) {
		///////////////////////접근제어/////////////////////////
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if(authUser == null) {
			return  "redirect:/";
		}
		//////////////////////////////////////////////////////
		userVO.setNo(authUser.getNo());
		userService.updateUser(userVO);
		return "redirect:/";
	}

	
/*	
	@ExceptionHandler(Exception.class)
	public String handleException() {
		return "error/exception";
	}
*/	
	
}
