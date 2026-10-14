package com.example.demo.controller;

import com.example.demo.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @ResponseBody
    @PostMapping("/chat")
    public String chat(@RequestParam String question) {
        return aiService.chat(question);
    }
    
    //  8080 호출
    @Controller
    public class PageController {
        @GetMapping("/")
        public String home() {
            return "chat.html";
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword, Model model) {

        if(keyword != null && !keyword.isBlank()){
            String answer = aiService.chat(keyword);
            model.addAttribute("answer", answer);
        }

        return "chat.html";
    }
}
