package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service

public class AIServiceByChatClient {

    private ChatClient chatClient;

    public AIServiceByChatClient(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generateText(String question) {
        return this.chatClient.prompt()
                .system("응답에 대해 친절하게 상냥하게 대답해줘.")
                .user(question)
                .options(OpenAiChatOptions.builder()
                        .temperature(0.3)
                        .maxTokens(1000))
                .call()
                .content();
    }


    public Flux<String> generateStreamText(String question) {
        return this.chatClient.prompt()
                .system("응답에 대해 친절하게 상냥하게 대답해줘.")
                .user(question)
                .options(OpenAiChatOptions.builder()
                        .temperature(0.3)
                        .maxTokens(1000))
                .stream()
                .content();
    }
}
