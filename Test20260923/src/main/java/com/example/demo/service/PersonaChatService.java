package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class PersonaChatService {

    private ChatClient chatClient;

    public PersonaChatService(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }

    SystemPromptTemplate systemPromptTemplate = SystemPromptTemplate.builder()
            .template("""
                  당신은 페르소나 AI 입니다.
                  사용자가 입력한 고민을 그 역할의 말투·전문성으로 조언해주세요.
                  부연 설명이나 추가적인 인사말 없이 번역된 결과 텍스트만 간결하게 출력하세요.
                  """)
            .build();

    PromptTemplate userTemplate = PromptTemplate.builder()
            .template("다음 사용자의 고민을 {role}로 조언해주세요.\n" +
                    "문장 : {question}")
            .build();

//    public Flux<String> promptTemplate(String role, String question){
//        Prompt prompt = userTemplate.create(Map.of("role", role, "question", question));
//        return chatClient.prompt(prompt)
//                .stream()
//                .content();
//    }

    public Flux<String> promptTemplate(String role, String question){
        return chatClient.prompt()
                .system(systemPromptTemplate.render())
                .user(userTemplate.render(Map.of("role", role, "question", question)))
                .stream()
                .content();
    }
}
