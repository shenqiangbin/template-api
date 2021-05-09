package com.example.demo.security;

import com.example.demo.base.Response;
import com.example.demo.base.util.JSONUtil;
import com.example.demo.model.LoginSuccessResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setStatus(HttpServletResponse.SC_OK);

        LoginSuccessResult loginSuccessResult = new LoginSuccessResult();
        loginSuccessResult.setSessionId(request.getSession().getId());
        MyUser myUser = (MyUser) authentication.getPrincipal();
        loginSuccessResult.setMyUser(myUser.toMyUserDTO());

        String str = JSONUtil.toString(Response.success(loginSuccessResult));

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(str);

        //super.onAuthenticationSuccess(request, response, authentication);
    }
}
