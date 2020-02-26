package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVO;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public Boolean join(UserVO vo) {
		int count = userRepository.insert(vo);
		return count == 1;
	}

	public UserVO getUser(UserVO vo) {
		return userRepository.findByEmailAndPassword(vo);
	}

	public UserVO getUser(Long no) {
		return userRepository.findByNo(no);
	}

	public boolean updateUser(UserVO userVO) {
		int count = userRepository.update(userVO);
		return count == 1;
	}
	
}
