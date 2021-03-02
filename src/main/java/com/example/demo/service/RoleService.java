package com.example.demo.service;

import com.example.demo.model.userInfo.Role;
import com.example.demo.model.userInfo.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public List<Role> getRolesByUserId(String userId) {
		return roleRepository.getRolesByUserId(userId);
	}
}
