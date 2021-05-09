package com.example.demo.security.access;

import com.example.demo.base.Response;
import com.example.demo.base.util.JSONUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        String str = JSONUtil.toString(new Response(HttpServletResponse.SC_FORBIDDEN,
                e.getMessage().equals("请设置权限") ? "权限不足(请设置此资源的访问权限)" : "权限不足"));
        httpServletResponse.getWriter().write(str);
    }
}