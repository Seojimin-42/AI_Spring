package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ModelComparisonService {

    private final ChatClient chatClient;

    public ModelComparisonService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public Flux<String> generateComparison_1(String question, String model) {
        return this.chatClient.prompt()
                .system("사용자 질문에 대해 한국어로 친절하게 대답해줘")
                .options(OllamaChatOptions.builder().model(model))
                .user(question)
                .stream()
                .content();
    }

    public Flux<String> generateComparison_2(String question, String model) {
        return this.chatClient.prompt()
                .system("사용자 질문에 대해 한국어로 친절하게 대답해줘")
                .options(OllamaChatOptions.builder().model(model))
                .user(question)
                .stream()
                .content();
    }


}
