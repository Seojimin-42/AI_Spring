package com.example.demo.service;

import com.example.demo.dto.Book;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AIBookService {
    private ChatClient chatClient;

    public AIBookService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    // bean 저수준 구조화 출력
    public Book lowLevel(String title) {
        BeanOutputConverter<Book> converter = new BeanOutputConverter(Book.class);

        PromptTemplate promptTemplate = PromptTemplate.builder()
                .template("책 {title}의 정보를 알려줘. {format}")
                .build();

        Prompt prompt = promptTemplate.create(
                Map.of("title", title, "format", converter.getFormat())
        );

        String result = chatClient
                .prompt(prompt)
                .call()
                .content();

        Book book = converter.convert(result);

        return book;
    }

    // bean 고수준 구조화 출력
    public Book highLevel(String title) {
        return chatClient
                .prompt()
                .user("책 %s의 정보를 알려줘.".formatted(title))
                .call()
                .entity(Book.class); // entity 안에 객체 생성을 안함.
    }
}
