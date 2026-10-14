package com.example.demo.controller;

import com.example.demo.service.FewshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class FewshotController {

    @Autowired
    private FewshotService fewshotService;

    @PostMapping("/fewshot")
    public String fewShot(String sentence){
        return fewshotService.fewShot(sentence);
    }
}
