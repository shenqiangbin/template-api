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
        String str = JSONUtil.toString(new Response(HttpServletResponse.SC_FORBIDDEN, "权限不足"));
        httpServletResponse.getWriter().write(str);
    }
}