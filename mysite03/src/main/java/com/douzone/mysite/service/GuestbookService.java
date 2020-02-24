package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVO;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookRepository guestbookRepository;

	public List<GuestbookVO> list() {
		List<GuestbookVO> list = guestbookRepository.findAll();
		return list;
	}

	public Boolean insert(GuestbookVO vo) {
		int count = guestbookRepository.insert(vo);
		return count == 1;
	}

	public void delete(Long no,String password) {
		guestbookRepository.delete(no,password);
	}
	
}
