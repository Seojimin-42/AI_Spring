package com.example.demo.service;

import org.springframework.ai.chat.model.ChatModel;
import org.apache.catalina.User;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AIService {

    @Autowired
    private ChatModel chatModel;

    // AI의 답변을 한 번에 받아오는 기능 (동기)
    public String generateText(String question) {
        SystemMessage systemMessage = SystemMessage.builder()
                .text("사용자 질문에 대해 영어로 답변하게 해줘.")
                .build();

        UserMessage userMessage = UserMessage.builder()
                .text(question)
                .build();

        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .model("openai/gpt-oss-20b")
                .temperature(0.3)
                .maxTokens(500)
                .build();

        Prompt prompt = Prompt.builder()
                .messages(systemMessage, userMessage)
                .chatOptions(chatOptions)
                .build();

        ChatResponse chatResponse = this.chatModel.call(prompt);
        Generation generation = chatResponse.getResult();
        AssistantMessage assistantMessage = generation.getOutput();
        String answer = assistantMessage.getText();
        return answer;
    }

    // AI의 답변을 조금씩 받아오는 기능 (비동기)
    public Flux<String> generateStreamText(String question) {
        SystemMessage systemMessage = SystemMessage.builder()
                .text("사용자 질문에 대해 영어로 답변하게 해줘.")
                .build();

        UserMessage userMessage = UserMessage.builder()
                .text(question)
                .build();

        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .model("openai/gpt-oss-20b")
                .temperature(0.3)
                .maxTokens(500)
                .build();

        Prompt prompt = Prompt.builder()
                .messages(systemMessage, userMessage)
                .chatOptions(chatOptions)
                .build();

        Flux<ChatResponse> fluxResponse = this.chatModel.stream(prompt);

        Flux<String> fluxString = fluxResponse.map(chatResponse -> {
            if(chatResponse.getResult() == null) return "";

            AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
            if(assistantMessage == null) return "";

            String chunk = assistantMessage.getText();
            if(chunk == null) return "";

            return chunk;

        });

        return fluxString;
    }
}
