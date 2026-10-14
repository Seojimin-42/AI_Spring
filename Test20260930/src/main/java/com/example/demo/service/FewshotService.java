package com.example.demo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class FewshotService {
    private ChatClient chatClient;
    
    public FewshotService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    
    public String fewShot(String sentence) {
        String strPrompt = """
                고객 일정을 위한 JSON 형식으로 바꿔주세요. 추가 설명은 포함하지 말고.
                
                예시1: 내일 3시 강남역 팀 회의가 있습니다.
                JSON 응답 :
                {
                    "title": "팀 회의",
                    "date": "내일",
                    "time": "15:00",
                    "place": "강남역"
                }
                
                예시2: 오늘 5시 동암역 미팅이 있습니다.
                JSON 응답 :
                {
                    "title": "미팅",  
                    "date": "오늘",
                    "time": "17:00",
                    "place": "동암역"
                }
                고객일정 : %s
                """.formatted(sentence);

        Prompt prompt = Prompt.builder()
                .content(strPrompt).build();

        return chatClient
                .prompt(prompt)
                .options(OpenAiChatOptions.builder().temperature(0.0).maxTokens(200))
                .call()
                .content();

    }
}
