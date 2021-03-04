package com.example.demo.security;

import com.example.demo.base.Response;
import com.example.demo.base.util.JSONUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        httpServletResponse.setContentType("application/json;charset=utf-8");
        String str = JSONUtil.toString(new Response(Response.NOT_LOGIN, "没有登录"));
        httpServletResponse.getWriter().write(str);
    }
}
