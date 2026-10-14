package com.example.demo.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AIStoryService {

    @Autowired
//    private ChatModel chatModel;

    public Flux<String> generateStoryStream(String topic) {

        SystemMessage systemMessage = SystemMessage.builder()
                .text("""
                        너는 동화 작가야.
                        사용자가 제시한 주제를 바탕으로 동화를 작성해줘.
                        한국어로 작성하고, 1000자 내외로 작성해줘.
                        """)
                .build();

        UserMessage userMessage = UserMessage.builder()
                .text(topic)
                .build();

        Prompt prompt = Prompt.builder()
                .messages(systemMessage, userMessage)
                .build();

        Flux<ChatResponse> fluxResponse = chatModel.stream(prompt);

        Flux<String> fluxString = fluxResponse.map(chatResponse -> {
            if (chatResponse.getResult() == null) return "";

            AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
            if (assistantMessage == null) return "";

            String chunk = assistantMessage.getText();
            if (chunk == null) return "";

            return chunk;
        });

        return fluxString;
    }
}
