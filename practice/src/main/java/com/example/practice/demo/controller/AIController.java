package com.example.practice.demo.controller;

import com.example.practice.demo.dto.Conversation;
import com.example.practice.demo.service.AIService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/chat-model")
    public Conversation chatModel(String question, String answer) {
        return aiService.generateText(question, answer);
    }

    @PostMapping("/chat-stream-model")
    public Mono<Conversation> chatStreamModel(String question, String answer) {
        return aiService.generateStreamText(question, answer);
    }

    @PostMapping("/search")
    public List<Document> search(String question) {
        return aiService.searchDocument(question);
    }
}
