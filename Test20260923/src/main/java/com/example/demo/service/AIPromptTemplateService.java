+package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AIPromptTemplateService {

    private ChatClient chatClient;

    public AIPromptTemplateService(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }

    PromptTemplate systemTemplate = SystemPromptTemplate.builder()
        .template("""
                  당신은 전문적이고 다양한 다국어 번역 AI 입니다.
                  사용자가 입력한 한국어 문장을 지정된 언어로 자연스럽게 번역하세요.
                  부연 설명이나 추가적인 인사말 없이 번역된 결과 텍스트만 간결하게 출력하세요.
                  """)
        .build();

    PromptTemplate userTemplate = PromptTemplate.builder()
            .template("다음 한국어 문장을 {language}로 번역해 주세요\n" +
                    "문장 : {statement}")
            .build();

    // 1. Prompt 객체를 만들어 전달
    public String promptTemplate1(String statement, String language){
        Prompt prompt = userTemplate.create(Map.of("statement", statement, "language", language));
        return chatClient.prompt(prompt)
                .call()
                .content();
    }

    // 2. messages()로 System/User 메시지 전달
    public String promptTemplate2(String statement, String language){
        return chatClient.prompt()
                .messages(systemTemplate.createMessage(),
                        userTemplate.createMessage(Map.of("statement", statement, "language", language)))
                .call()
                .content();
    }

    // 3. render()로 문자열을 만든 뒤 .system(), .user()에 전달
    public String promptTemplate3(String statement, String language){
        return chatClient.prompt()
                .system(systemTemplate.render())
                .user(userTemplate.render(Map.of("statement", statement, "language", language)))
                .call()
                .content();
    }

    // 4. Template 없이 Java 문자열과 .formatted() 사용
    public String promptTemplate4(String statement, String language){
        String systemText = """
                                당신은 전문적이고 다양한 다국어 번역 AI 입니다.
                                사용자가 입력한 한국어 문장을 지정된 언어로 자연스럽게 번역하세요.
                                부연 설명이나 추가적인 인사말 없이 번역된 결과 텍스트만 간결하게 출력하세요.
                            """;
        String userText = """
                            다음 한국어 문장을 %s로 번역해 주세요\n
                            문장 : %s
                          """.formatted(language, statement);
        return chatClient.prompt()
                .system(systemText)
                .user(userText)
                .call()
                .content();
    }
}
