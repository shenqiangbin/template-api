package com.example.demo.model;

import org.springframework.security.core.userdetails.UserDetails;

public class LoginSuccessResult {

    private UserDetails userDetails;
    private String sessionId;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
