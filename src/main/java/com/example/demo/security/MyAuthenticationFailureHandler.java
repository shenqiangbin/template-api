package com.example.demo.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String username = httpServletRequest.getParameter("username");

        String flag = "true";
        Cookie cookie = new Cookie("showcode", "1");
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
        if ("true".equals(flag)) {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
            httpServletResponse.getWriter().write(e.getMessage());
        } else {
            handler.setDefaultFailureUrl("/login?error=true");
            handler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
        }
    }
}
