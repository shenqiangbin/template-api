package com.example.demo.controller;

import com.example.demo.base.Response;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/user/info")
    private String info() {
        return "info";
    }

    @GetMapping("/tel/login")
    public Response<String> telLogin(String tel, String smsCode, HttpSession session) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));
        Authentication auth = new UsernamePasswordAuthenticationToken("tel", "", authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String sessionId = session.getId();
        return new Response(200, sessionId);
    }

    @PostMapping("/user/changePwd")
    public Response changePwd(String oldPwd, String newPwd, Principal user, HttpSession session) {

        Response r = new Response();

        try {

            String loginUser = user.getName();
            System.out.println(loginUser);
            User usr = userService.getUserByUserCode(loginUser);

            if (usr.getPassword().equals(oldPwd)) {
                boolean success = userService.ChangePwd(loginUser, newPwd);
                if (success) {

                    //令 Session 失效，表示退出登录
                    session.invalidate();
                    //SecurityContextHolder.clearContext();

                    r.set(200, "密码更新成功");
                } else {
                    r.set(501, "密码更新失败");
                }
            } else {
                r.set(400, "原密码不正确");
            }

        } catch (Exception e) {
            r.set(501, "密码更新失败，出现异常");

            logger.error(e.getMessage() + e.getStackTrace());
        }

        return r;
    }

}
