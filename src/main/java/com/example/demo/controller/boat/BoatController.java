package com.example.demo.controller.boat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoatController {

    @ResponseBody
    @GetMapping("boat/index")
    public String index() {
        return "the boat index page";
    }

    @ResponseBody
    @GetMapping("boat/remove")
    public String remove() {
        return "the boat remove page";
    }
}
