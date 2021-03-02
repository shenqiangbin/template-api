package com.example.demo.security.access;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

	/*
	 * @param authentication 当前用户
	 * 
	 * @param object 要访问的资源
	 * 
	 * @param configAttributes 安全对象关联的配置属性
	 *  //Security需要用到一个实现了AccessDecisionManager接口的类
		//类功能：根据当前用户的信息，和目标url涉及到的权限，判断用户是否可以访问
		//判断规则：用户只要匹配到目标url权限中的一个role就可以访问
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

		if (configAttributes == null)
			return;

		for (ConfigAttribute configAttribute : configAttributes) {
			String attribute = configAttribute.getAttribute();

			for (GrantedAuthority authority : authentication.getAuthorities()) {
				if(authority.getAuthority().equals(attribute))
					return;
			}
		}
		
		throw new AccessDeniedException("权限不足");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
