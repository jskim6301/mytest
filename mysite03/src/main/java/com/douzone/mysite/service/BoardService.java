package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVO;

@Service
public class BoardService {
	
	private static final int postNum = 10;
	private static final int pageNum_cnt = 10; 		//한번에 표시할 페이징 번호의 갯수
	
	@Autowired
	private BoardRepository boardRepository;
	


	public Map<String,Object> getContentsList(int currentPage,String keyword) { //초기값 page=1(int), keyword = "", option =""
		int count = boardRepository.getTotalCount(keyword);
		int startPageNum;
		int endPageNum;
		
		boolean prev;
		boolean next;
		
		int displayPost = (currentPage-1) * postNum;
		

		//표시되는 페이지 번호 중 마지막 번호
		endPageNum = (int)(Math.ceil((double)currentPage/(double)pageNum_cnt) * pageNum_cnt);
		//표시되는 페이지 번호 중 첫 번째 번호
		startPageNum = endPageNum - (pageNum_cnt - 1);
		
		//마지막 번호 재계산
		int endPageNum_tmp = (int)(Math.ceil((double)count / (double)pageNum_cnt));
		
		if(endPageNum > endPageNum_tmp) {
			endPageNum = endPageNum_tmp;
		}
		
		prev = startPageNum == 1 ? false : true;
		next = endPageNum * pageNum_cnt >= count ? false : true;
		
		List<BoardVO> list = boardRepository.findAll(displayPost,postNum,keyword);
		
		//하단 페이징 번호( [게시물 총 갯수 + 한페이지에 출력할 갯수]의 올림)
		int pageNum = (int)Math.ceil((double)count/postNum);
		System.out.println(pageNum);//308
		
		Map<String, Object> map = new HashMap<String, Object>();
		//시작 및 끝 번호
		map.put("startPageNum", startPageNum);
		map.put("endPageNum", endPageNum);
		
		//이전 및 다음
		map.put("prev", prev);
		map.put("next", next);
		
		//현재 페이지
		map.put("currentPage", currentPage);
		
		map.put("displayPost", displayPost);
		map.put("count", count);
		map.put("pageNum",pageNum);
		map.put("list", list);
		
		return map;
	}



	public BoardVO getContents(Long no) {
		BoardVO boardVO = boardRepository.findByNo(no);
		
		if(boardVO != null) {
			boardRepository.updateHit(no);
		}
		
		return boardVO;
	}



	public boolean addContents(BoardVO boardVO) {
		return boardRepository.insert(boardVO) == 1;		
	}



	public boolean increaseGroupOrderNo(BoardVO boardVO) {
		return boardRepository.updateSequece(boardVO.getgNo(),boardVO.getoNo()) > 0; 
		
	}

}
