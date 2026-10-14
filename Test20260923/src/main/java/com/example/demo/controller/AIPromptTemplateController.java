package com.example.demo.controller;

import com.example.demo.service.AIPromptTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AIPromptTemplateController {

    @Autowired
    private AIPromptTemplateService aiServicePromptTemplate;

    @PostMapping("/prompt1")
    public String prompt1(String statement, String language){
        return aiServicePromptTemplate.promptTemplate1(statement, language);
    }


}
