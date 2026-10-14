package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIRecipeService {

    private final ChatClient chatClient;

    public AIRecipeService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generateRecipe(String ingredients) {
            return this.chatClient.prompt()
                .system("""
                        너는 전문 요리사야.
                        사용자가 재료를 입력하면, 그 재료로 만들 수 있는 3가지 레시피를 추천해줘.
                        한국어로 작성하고, 각 요리의 조리법을 2~3문장으로 지시해줘.
                        """)
                .user(ingredients)
                .options(OpenAiChatOptions.builder()
                        .temperature(0.8)
                        .maxTokens(500))
                .call()
                .content();
    }
}
