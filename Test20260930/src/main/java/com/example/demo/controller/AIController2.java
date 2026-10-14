package com.example.demo.controller;

import com.example.demo.dto.Hotel;
import com.example.demo.service.AIService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/ai")
public class AIController2 {

    @Autowired
    private AIService2 aiService2;

    // list 구조화 출력
    @PostMapping("/listoutput1")
    public List<String> listOutput1(String city){
        return aiService2.listOutput1(city);
    }

    @PostMapping("/listoutput2")
    public List<String> listOutput2(String city){
        return aiService2.listOutput2(city);
    }

    // bean 구조화 출력
    @PostMapping("/beanoutput1")
    public Hotel beanOutput1(String city){
        return aiService2.beanOutput1(city);
    }

    @PostMapping("/beanoutput2")
    public Hotel beanOutput2(String city){
        return aiService2.beanOutput2(city);
    }

    // beanlist 구조화 출력
    @PostMapping("/beanlistoutput1")
    public List<Hotel> beanListOutput1(String cities){
        return aiService2.beanListOutput1(cities);
    }

    @PostMapping("/beanlistoutput2")
    public List<Hotel> beanListOutput2(String cities){
        return aiService2.beanListOutput2(cities);
    }

    // map 구조화 출력
    @PostMapping("/mapoutput1")
    public Map<String, Object> mapOutput1(String hotel){
        return aiService2.mapOutput1(hotel);
    }

    @PostMapping("/mapoutput2")
    public Map<String, Object> mapOutput2(String hotel){
        return aiService2.mapOutput2(hotel);
    }

}
