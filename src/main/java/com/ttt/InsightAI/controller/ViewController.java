package com.ttt.InsightAI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping
    public String main() {
        return "index";
    }

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
        return "main_diary";
    }

    @GetMapping("/diary-analysis")
    public String analysis() {
        return "analysis";
    }

    @GetMapping("/{email}/diaries")
    public String diaries() {
        return "diaries";
    }
}
