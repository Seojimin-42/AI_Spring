package com.example.demo.controller;

import com.example.demo.service.AIService;
import com.example.demo.service.ModelComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class ModelComparisonController {

    @Autowired
    private ModelComparisonService modelComparisonService;

    @PostMapping("/ollamachat")
    public Flux<String> chat(String question, String model){
        return modelComparisonService.generateComparison_1(question, model);
    }

    @Controller
    public class ModelComparisonPageController {
        @GetMapping("/compare")
        public String compare() { return "compare"; }
    }
}


