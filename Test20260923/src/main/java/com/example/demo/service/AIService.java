package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private ChatClient chatClient;

    public AIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String chat(String question) {
        return chatClient.prompt()
                .system("사용자 질문에 대해 일본어로 대답해줘.")
                .options(OllamaChatOptions.builder().model("exaone3.5:2.4b")) // 동적으로 모델을 바꿀 수 있음.
                .user(question)
                .call()
                .content();
    }
}
