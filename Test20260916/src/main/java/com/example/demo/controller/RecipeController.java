package com.example.demo.controller;

import com.example.demo.service.AIRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class RecipeController {

    @Autowired
    private AIRecipeService aiRecipeService;

    @PostMapping("/recipe")
    public String chatClient(String ingredients){
        return aiRecipeService.generateRecipe(ingredients);
    }

    @Controller
    public class RecipePageController{
        @GetMapping("/recipe")
        public String recipe() {
            return "recipe";
        }
    }
}
