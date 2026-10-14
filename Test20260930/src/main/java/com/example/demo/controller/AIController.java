package com.example.demo.controller;

import com.example.demo.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/order")
    public String chatOrder(String order){
        return aiService.chatOrder(order);
    }

    @PostMapping("/stepback")
    public String stepBack(String question){
        return aiService.stepBack(question);
    }
}
