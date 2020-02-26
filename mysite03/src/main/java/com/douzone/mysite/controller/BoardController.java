package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVO;
import com.douzone.mysite.vo.UserVO;
import com.douzone.web.util.WebUtil;


@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="")
	public String list(
			@RequestParam(value="num",required=true,defaultValue="1") int page,
			@RequestParam(value="kwd",required=true,defaultValue="") String keyword,
			Model model) {
		
		Map<String,Object> map = boardService.getContentsList(page,keyword);
		model.addAttribute("map",map);
		
//		model.addAllAttributes(map);//jsp화면에서 이름으로 바로 접근
		return "board/list";
	}
	
	@RequestMapping(value= "/view/{no}",method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no,Model model) {
		BoardVO boardVO = boardService.getContents(no);
		model.addAttribute("boardVO",boardVO);
		return "board/view";
	}
	
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public String write(HttpSession session) {
		return "board/write";
	}
	
	@RequestMapping(value="/write",method=RequestMethod.POST)
	public String write(HttpSession session,BoardVO boardVO,
			@RequestParam(value="p",required=true,defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true,defaultValue="") String keyword) {
		//////////////////////////////접근제어//////////////////////////
		UserVO authUser = (UserVO)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		//////////////////////////////////////////////////////////////
		
		boardVO.setUserNo(authUser.getNo()); //로그인 한 사람이 답글을 달 수 있도록 한다.
		if(boardVO.getgNo() != null) { //그룹넘버가 있다는 말은 
			boardService.increaseGroupOrderNo(boardVO);
		}
		boardService.addContents(boardVO); //처음 넣는 것
		
		return (boardVO.getgNo()!= null) ? "redirect:/board?p="+page+"&kwd="+WebUtil.encodeURL(keyword,"UTF-8") : "redirect:/board";
	}
	@RequestMapping(value="/reply/{no}")
	public String reply(@PathVariable("no") Long no, Model model) {
		BoardVO boardVO = boardService.getContents(no);
		boardVO.setoNo(boardVO.getoNo()+1);
		boardVO.setDepth(boardVO.getDepth()+1);
		model.addAttribute("boardVO",boardVO);
		
		return "board/reply";
	}
}

