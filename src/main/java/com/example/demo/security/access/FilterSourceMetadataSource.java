package com.example.demo.security.access;

import com.example.demo.model.userInfo.ResourceRole;
import com.example.demo.service.ResourceRoleService;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FilterSourceMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceRoleService resourceRoleService;

    @Override
    //接收用户请求的地址，返回访问该地址需要的所有权限
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        //得到用户的请求地址,控制台输出一下
//        String requestUrl = ((FilterInvocation) object).getRequestUrl();
//        System.out.println("用户请求的地址是：" + requestUrl);
//
//        if (requestUrl.equalsIgnoreCase("/user/info")) {
//            return SecurityConfig.createList("role1", "role2");
//        }

        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        Collection<ConfigAttribute> cList = new ArrayList<>();

        List<ResourceRole> list = getResourceRole();
        for (ResourceRole rr : list) {
            String[] apis = rr.getApis().split(";");

            for (String api : apis) {
                if (StringUtils.isEmpty(api)) {
                    continue;
                }

                AntPathRequestMatcher matcher = new AntPathRequestMatcher(api);
                if (matcher.matches(request)) {
                    cList.add(new SecurityConfig(rr.getRoleName()));
                }
            }
        }

        if (cList.size() > 0) {
            return cList;
        } else {
            throw new AccessDeniedException("请设置权限");
        }
        //return cList;
        //无权限限制
        //return null;
    }

    private List<ResourceRole> getResourceRole() {
        return resourceRoleService.getAll();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // 要修改为true,否则会提示 SecurityMetadataSource does not support secure object class: class org.springframework.security.web.FilterInvocation
        return true;
    }

}
