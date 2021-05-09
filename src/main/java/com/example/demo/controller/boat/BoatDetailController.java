package com.example.demo.controller.boat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoatDetailController {

    @ResponseBody
    @GetMapping("boatDetail/index")
    public String index() {
        return "the boatDetail index page";
    }

    @ResponseBody
    @GetMapping("boatDetail/remove")
    public String remove() {
        return "the boatDetail remove page";
    }
}
