package com.example.demo.model;

import com.example.demo.security.MyUser;

public class LoginSuccessResult {

    private MyUser.MyUserDTO myUser;
    private String sessionId;

    public MyUser.MyUserDTO getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser.MyUserDTO myUser) {
        this.myUser = myUser;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
