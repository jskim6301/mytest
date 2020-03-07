package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVO;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join",method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	@RequestMapping(value="/join",method=RequestMethod.POST)
	public String join(UserVO vo) {
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
//	@RequestMapping(value="/login",method=RequestMethod.POST)
//	public String login(HttpSession session,@ModelAttribute UserVO vo) {
//		UserVO authUser = userService.getUser(vo); //email과 password만 받아와서 no와 name값을 받아냄
//		if(authUser == null) {
//			return "user/login";
//		}
//		session.setAttribute("authUser", authUser);
//		return "redirect:/";
//	}
//	@RequestMapping(value="/logout")
//	public String logout(HttpSession session) {
//		/*
//		 * 제 3자(authUser가 아닌)가 접근 제어가 없다면 authUser를 제거해 버린다.
//		 */
//		/////////////////////////접근제어/////////////////////////////////////
//		UserVO authUser = (UserVO)session.getAttribute("authUser");
//		if(authUser == null) {
//			return "redirect:/";
//		}		
//		///////////////////////////////////////////////////////////////////		
//		session.removeAttribute("authUser");
//		session.invalidate();
//		return "redirect:/";
//	}
	
	@Auth
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(@AuthUser UserVO authUser,Model model) {
//		UserVO authUser = (UserVO)session.getAttribute("authUser");
		Long no = authUser.getNo();//no와name값을 가진 authUser
		
		UserVO vo = userService.getUser(no);//no,name,email,password,gender,join_date 반환
		model.addAttribute("userVO",vo);
		return "user/update";
	}
	@Auth
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(@AuthUser UserVO authUser,UserVO userVO) {
		
//		UserVO authUser = (UserVO) session.getAttribute("authUser");
//		if(authUser == null) {
//			return "redirect:/";
//		}
//		UserVO authUser = (UserVO) session.getAttribute("authUser");
		//update를 하려면 where 절에 기준값이 있어야 한다 name은 중복가능성이 있기때문에 안되고 no를 기준으로 값을 구분 해야한다.
		userVO.setNo(authUser.getNo());
		userService.updateUser(userVO);	//받아온 userVO에는 name,password,gender 만 있다. authUser에서 no값을 추가 	
		return "redirect:/";
	}
	
}
