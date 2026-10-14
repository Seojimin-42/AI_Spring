package com.example.demo.controller;

import com.example.demo.dto.Book;
import com.example.demo.service.AIBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AIBookController {
    @Autowired
    private AIBookService aiBookService;

    @PostMapping("/book1")
    public Book lowLevel(String title) {
        return aiBookService.lowLevel(title);
    }

    @PostMapping("/book2")
    public Book highLevel(String title) {
        return aiBookService.highLevel(title);
    }
}
