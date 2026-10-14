package com.example.demo.controller;

import com.example.demo.service.AIService;
import com.example.demo.service.AIServiceByChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AIController {
    @Autowired
    private AIServiceByChatClient aiService;
//    private AIService aiService;

    @PostMapping("/chat-model")
    public String chatModel(String question){
        return aiService.generateText(question);
    }

    @PostMapping("/chat-stream-model")
    public Flux<String> chatStreamModel(String question){
        return aiService.generateStreamText(question);
    }
}
