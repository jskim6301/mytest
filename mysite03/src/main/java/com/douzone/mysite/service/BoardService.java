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
	private static final int pageNum_cnt = 10;
	
	@Autowired
	private BoardRepository boardRepository;
	
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
		int count = boardRepository.getTotalCount(keyword);
		
		int displayPost = (currentPage -1) * postNum;
				
		int endPageNum = (int)(Math.ceil((double)currentPage/(double)pageNum_cnt) * pageNum_cnt);// currentPage 가 156일 경우 endPageNum 은 160
		int startPageNum = endPageNum - (pageNum_cnt-1);//startPageNum은 151~160
		
		int endPageNum_tmp = (int)(Math.ceil((double)count/(double)pageNum_cnt));
		if(endPageNum > endPageNum_tmp) {
			endPageNum = endPageNum_tmp;
		}
		boolean prev = startPageNum == 1 ? false : true;//시작 페이지 번호가 1일 때를 제외하곤 무조건 출력
		
		boolean next = endPageNum * pageNum_cnt >= count ? false : true;//마지막 페이지 번호가 총 게시물 갯수보다 작다면, 다음 구간이 있다는 의미이므로 출력
		
		List<BoardVO> list = boardRepository.findAll(displayPost,postNum,keyword);
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("list",list);
		
		map.put("prev",prev);
		map.put("next",next);
		
		map.put("count",count);
		map.put("currentPage",currentPage);
		map.put("displayPost",displayPost);
		
		map.put("startPageNum",startPageNum);
		map.put("endPageNum",endPageNum);
		
		map.put("keyword",keyword);
		
		return map;
	}

	public BoardVO getContents(Long no) {
		BoardVO boardVO = boardRepository.findByNo(no);
		if(boardVO != null) {
			boardRepository.updateHit(no);
		}
		return boardVO;
	}

	public Boolean addContents(BoardVO boardVO) {
		if(boardVO.getgNo() != null) {
			increaseGroupOrderNo(boardVO);
		}
		return boardRepository.insert(boardVO) == 1;
	}

	private Boolean increaseGroupOrderNo(BoardVO boardVO) {
		return boardRepository.updateSequence(boardVO.getgNo(),boardVO.getoNo()) > 0;
	}

	public Boolean deleteContents(Long boardNo, Long userNo) {
		int count = boardRepository.delete(boardNo,userNo);
		return count == 1;
	}

	public BoardVO getContents(Long boardNo, Long userNo) {
		BoardVO boardVO = boardRepository.findByNoAndUserNo(boardNo,userNo);
		return boardVO;
	}

	public Boolean modifyContents(BoardVO boardVO) {
		int count = boardRepository.update(boardVO);
		return count == 1;
		
	}
	

}
