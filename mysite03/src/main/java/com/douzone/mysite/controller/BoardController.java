package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
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
			@RequestParam(value="p",required=true,defaultValue="1") int currentPage,
			@RequestParam(value="kwd",required=true,defaultValue="") String keyword,
			Model model) {
		
		Map<String,Object> map = boardService.getContentsList(currentPage,keyword);
		model.addAttribute("map",map);
		return "board/list";
	}
	
	
	@RequestMapping(value="/view/{no}",method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no,Model model) {
		BoardVO boardVO = boardService.getContents(no);
		model.addAttribute("boardVO",boardVO);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="write",method=RequestMethod.POST)
	public String write(
			@AuthUser UserVO authUser,
			@ModelAttribute BoardVO boardVO,
			@RequestParam(value="p",required=true,defaultValue="1") Integer page,
			@RequestParam(value="kwd",required=true,defaultValue="") String keyword) {
		
		boardVO.setUserNo(authUser.getNo());
		boardService.addContents(boardVO);
		
		return "redirect:/board?p="+page+"&kwd="+WebUtil.encodeURL(keyword, "UTF-8");
	}
	
	@Auth
	@RequestMapping(value="/reply/{no}")
	public String reply(
			@AuthUser UserVO authUser,
			@PathVariable("no") Long no,
			Model model) {
		
		BoardVO boardVO = boardService.getContents(no);
		boardVO.setoNo(boardVO.getoNo()+1);
		boardVO.setDepth(boardVO.getDepth()+1);
		model.addAttribute("boardVO",boardVO);
		
		return "board/reply";
	}
		
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(
			@AuthUser UserVO authUser,
			@PathVariable("no") Long no,
			@RequestParam(value="p",required=true,defaultValue="1") Integer page,
			@RequestParam(value="kwd",required=true,defaultValue="") String keyword) {
		
			boardService.deleteContents(no,authUser.getNo());
			return "redirect:/board?p="+page+"&kwd="+WebUtil.encodeURL(keyword, "UTF-8");
	}
	
	@Auth
	@RequestMapping(value="/modify/{no}")
	public String modify(
			@AuthUser UserVO authUser,
			@PathVariable("no") Long no,
			Model model) {
			
			BoardVO boardVO = boardService.getContents(no,authUser.getNo());
			model.addAttribute("boardVO",boardVO);
			return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modify(
			@AuthUser UserVO authUser,
			@ModelAttribute BoardVO boardVO,
			@RequestParam(value="p",required=true,defaultValue="1") Integer page,
			@RequestParam(value="kwd",required=true,defaultValue="") String keyword) {
			
			boardVO.setUserNo(authUser.getNo());
			boardService.modifyContents(boardVO);
			return "redirect:/board/view"+boardVO.getNo()+"?p="+page+"&kwd="+WebUtil.encodeURL(keyword, "UTF-8");
	}
}

