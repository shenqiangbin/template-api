package com.example.demo.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String username = request.getParameter("username");
        String flag = "true";
//        Cookie cookie = new Cookie("showcode", "");
//        cookie.setPath("/");
//        response.addCookie(cookie);
        if ("true".equals(flag)) {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
            //JSONObject ret = new JSONObject();
            try {
                //String func = ((LoginUserInfo) authentication.getPrincipal()).getFunc();

//                ret.put("code", 200);
//                ret.put("msg", "succese");
//                ret.put("csrf", csrfToken.getToken());
//                ret.put("func", func);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
            response.setStatus(200);
            response.getWriter().write("{\"code\":\"?\"}".replace("?", request.getSession().getId()));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
