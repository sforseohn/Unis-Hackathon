package com.ttt.InsightAI.controller;

import com.ttt.InsightAI.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signUp() {
        return "join";
    }

    @GetMapping("/process")
    public String process(){
        return "process";
    }

    @GetMapping("/friend")
    public String choosingFriend(){
        return "friendChoice";
    }

    @GetMapping("/category")
    public String choosingCategory(){
        return "category";
    }

    @GetMapping("/q1")
    public String question1(){
        return "Q1";
    }

    @GetMapping("/q2")
    public String question2(){
        return "Q2";
    }

    @GetMapping("/q3")
    public String question3(){
        return "Q3";
    }

    @GetMapping("/q4")
    public String question4(){
        return "Q4";
    }

    @GetMapping("/q5")
    public String question5(){
        return "Q5";
    }

    @GetMapping("/q6")
    public String question6(){
        return "Q6";
    }

    @GetMapping("/result")
    public String result() {
        return "result";
    }

    @GetMapping("/todolist")
    public String todolist() {
        return "todolist";
    }


    @GetMapping("/{name}/diaries")
    public String diaries() {
        return "TTT_diaries";
    }
}
