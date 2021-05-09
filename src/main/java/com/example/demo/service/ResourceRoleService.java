package com.example.demo.service;

import com.example.demo.model.userInfo.ResourceRole;
import com.example.demo.repository.ResourceRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ResourceRoleService {

	@Autowired
	private ResourceRoleRepository resourceRoleRepository;

	public List<ResourceRole> getAll(){
		return resourceRoleRepository.getAll();
	}
}
