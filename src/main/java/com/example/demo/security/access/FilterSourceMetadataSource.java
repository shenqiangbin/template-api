package com.example.demo.security.access;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FilterSourceMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Override
	//接收用户请求的地址，返回访问该地址需要的所有权限
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		//得到用户的请求地址,控制台输出一下
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        //System.out.println("用户请求的地址是：" + requestUrl);
		
		//return null; //无权限限制
		return SecurityConfig.createList("user","role2");
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		// 要修改为true,否则会提示 SecurityMetadataSource does not support secure object class: class org.springframework.security.web.FilterInvocation
		return true;
	}

}
