package com.example.demo.controller;

import com.example.demo.base.Response;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

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
