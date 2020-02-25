package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVO;

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
		userService.join(vo);
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
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(HttpSession session, @ModelAttribute UserVO vo) {
		
		UserVO authUser =  userService.getUser(vo);
		if(authUser == null) {
			return "user/login";
		}
		
		session.setAttribute("authUser", authUser);
		return "redirect:/";
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		/////////////////////////접근제어/////////////////////////////////////
		UserVO authUser = (UserVO)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}		
		//////////////////////////////////////////////////////////////
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(HttpSession session,Model model) {
		/////////////////////////접근제어/////////////////////////////////////		
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		//////////////////////////////////////////////////////////////
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
		return "redirect:/user/update";
	}

	
/*	
	@ExceptionHandler(Exception.class)
	public String handleException() {
		return "error/exception";
	}
*/	
	
}
