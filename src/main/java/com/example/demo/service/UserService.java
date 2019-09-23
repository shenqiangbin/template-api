package com.example.demo.service;

import com.example.demo.model.User;

import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUserByUserCode(String userCode) throws Exception{
		return userRepository.getUserByUserCode(userCode);
	}

	public boolean ChangePwd(String loginUser, String newPwd) {
		return userRepository.ChangePwd(loginUser,newPwd);
	}
}
