package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;

@RestController
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "mgr index page";
    }

    @GetMapping("/login")
    public String login(){
        return "login page";
    }
}
