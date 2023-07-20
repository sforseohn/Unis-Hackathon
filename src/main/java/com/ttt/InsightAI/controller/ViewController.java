package com.ttt.InsightAI.controller;

import com.ttt.InsightAI.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String login(Model model) {

        return "login";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "join";
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
