package com.ttt.InsightAI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @GetMapping("/diary")
    public String diary() {
        return "TTT_main_diary";
    }

    @GetMapping("/diary-analysis")
    public String analysis() {
        return "TTT_analysis";
    }

    @GetMapping("/{name}/diaries")
    public String diaries() {
        return "TTT_diaries";
    }
}
