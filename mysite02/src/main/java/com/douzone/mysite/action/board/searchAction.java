package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class searchAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
//		int count = new BoardRepository().count();// 
		int postNum = 10;
		int Num;
		int displayPost;
		
		int startPageNum;
		int endPageNum;
		
		boolean prev;
		boolean next;
		
		List<BoardVO> list = null;		
		
		BoardVO boardVO;
		
		
		if( request.getParameter("kwd").isEmpty() ) {
			WebUtil.redirect(request.getContextPath()+"/board?num=1", request, response);
			return;
		}
		String kwd = request.getParameter("kwd");//사용자 입력값
		
		String option = request.getParameter("option"); //제목 or 글쓴이

		String num = request.getParameter("num");
		
		Num = Integer.parseInt(num);

		displayPost = (Num-1)*postNum;
		
		int Count = 0;
		if(option.equals("title")) { //제목으로 찾기라면
			list = new BoardRepository().findByTitle(kwd,displayPost,postNum); //입력된 값에 해당하는 것들의 리스트들
			Count = new BoardRepository().countTitle(kwd);
			
		}else if(option.equals("name")) {//이름으로 찾기라면
			list = new BoardRepository().findByName(kwd,displayPost,postNum);
			Count = new BoardRepository().countName(kwd);
		}		//ㅇㅇㅇ
		//한번에 표시할 페이징 번호의 갯수
		int pageNum_cnt = 10;
		//표시되는 페이지 번호 중 마지막 번호
		endPageNum = (int)(Math.ceil((double)Num / (double)pageNum_cnt) * pageNum_cnt);
		
		//표시되는 페이지 번호 중 첫번째 번호
		startPageNum = endPageNum - (pageNum_cnt - 1);
		

		
		
		//마지막 번호 재계산
		int endPageNum_tmp = (int)(Math.ceil((double)Count / (double)pageNum_cnt));
		
		if(endPageNum > endPageNum_tmp) {
			endPageNum = endPageNum_tmp;
		}
		
		prev = startPageNum == 1 ? false : true;
		next = endPageNum * pageNum_cnt >= Count ? false : true;
		
		//하단 페이징 번호( [게시물 총 갯수 + 한페이지에 출력할 갯수]의 올림)
		int pageNum = (int)Math.ceil((double)Count/postNum);
		
		
		
		
		//시작 및 끝 번호
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		
		//이전 및 다음
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);
		
		//현재 페이지
		request.setAttribute("select", Num);
		
		request.setAttribute("displayPost", displayPost);
		request.setAttribute("count", Count);
		request.setAttribute("pageNum",pageNum);
		
		//값을 유지하기위한 처리
		request.setAttribute("kwd", kwd);
		request.setAttribute("option", option);
		
		request.setAttribute("list", list);
		WebUtil.forward("/WEB-INF/views/board/searchlist.jsp", request, response);
		return;		

	}

}
