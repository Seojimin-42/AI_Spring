package com.example.demo.controller;

import com.example.demo.service.AIStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class StoryController {

    @Autowired
    private AIStoryService aiStoryService;

    @PostMapping(value = "/story-stream", produces = "application/x-ndjson")
    public Flux<String> storyStream(@RequestParam String topic) {
        return aiStoryService.generateStoryStream(topic);
    }

    @Controller
    public class StoryPageController {
        @GetMapping("/story")
        public String story() {
            return "story";
        }
    }
}
