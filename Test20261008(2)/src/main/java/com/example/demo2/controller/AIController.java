package com.example.demo2.controller;

import com.example.demo2.dto.Hit;
import com.example.demo2.service.AIService;

import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ai")
public class AIController {
    @Autowired
    private AIService aiService;

    @PostMapping("/add")
    public void addDocument() {
        aiService.addDocument();
    }

    // 기본 유사도 검색 (반환 형태 String)
    @PostMapping("/search")
    public String searchDocument(String question) {
        List<Document> documentList = aiService.searchDocument(question);

        String text = "";

        for (Document document : documentList) { // List<Document>로 받은 후 하나씩 꺼냄.
            text += document.getText() // 각 문서에서
                    + " 유사도 : "
                    + document.getScore() // 문서 본문
                    + document.getMetadata().get("year") // 유사도 점수
                    + "\n";
        }
        return text;
    }

    // 기본 유사도 검색 (반환 형태 List<Hit>)
    @PostMapping("/search2")
    public List<Hit> searchDocument2(String question) {
        List<Document> documentList = aiService.searchDocument(question);
        List<Hit> result = new ArrayList<>();

        for (Document document : documentList) {
            result.add(
                    new Hit(
                        document.getText(),
                        document.getScore(),
                        document.getMetadata().get("year")
                    )
            );
        }
        return result;
    }

    // topK, threshold, filter 조건을 적용한 유사도 검색 (반환 형태 List<Hit>)
    @PostMapping("/search3")
    public List<Hit> searchDocument3(String question) {
        List<Document> documentList = aiService.searchDocument3(question);

        List<Hit> result = new ArrayList<>();

        for (Document document : documentList) {
            result.add(
                new Hit(document.getText(),
                        document.getScore(),
                        document.getMetadata().get("year")
                )
            );
        }
        return result;
    }
}
