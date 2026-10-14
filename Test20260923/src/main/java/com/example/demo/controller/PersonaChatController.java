package com.example.demo.controller;

import com.example.demo.service.PersonaChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class PersonaChatController {

    @Autowired
    private PersonaChatService personaChatService;

    @PostMapping("/persona")
    public Flux<String> persona(String role, String question){
        return personaChatService.promptTemplate(role, question);
    }

    @Controller
    public class PersonaPageController {


        @GetMapping("/persona")
        public String persona() {
            return "persona";
        }
    }
}
