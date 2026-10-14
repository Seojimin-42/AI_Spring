package com.example.practice.demo.service;

import com.example.practice.demo.dto.Conversation;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class AIService {

    private ChatClient chatClient;

    public AIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    // ChatClient 동기 처리 고수준 + 동적 PromptTemplate(자바 표준 형태) + BeanOutputConverter
    public Conversation generateText(String question, String answer) {

        PromptTemplate promptTemplate = PromptTemplate.builder()
                .template("""
                        질문: {question}
                        답변: {answer}
                        위 답변을 한 문장으로 요약해줘.
                        """.formatted(question, answer))
                .build();

        Prompt prompt = promptTemplate.create(
                Map.of("question", question, "answer", answer)
        );

        return this.chatClient
                .prompt(prompt)
                .options(OpenAiChatOptions.builder()
                        .temperature(0.3)
                        .maxTokens(1000))
                .call()
                .entity(Conversation.class); // entity 안에 객체 생성 안함.
    }

    // ChatClient 비동기 처리 고수준 + 동적 PromptTemplate + BeanOutputConverter 저수준
    public Mono<Conversation> generateStreamText(String question, String answer) {

        BeanOutputConverter<Conversation> converter
                = new BeanOutputConverter<>(Conversation.class);

        PromptTemplate promptTemplate = PromptTemplate.builder()
                .template("질문: {question} \n 답변: {answer} \n 위 답변을 한 문장으로 요약해줘.")
                .build();

        Prompt prompt = promptTemplate.create(
                Map.of("question", question, "answer", answer, "format", converter.getFormat())
        );

        return this.chatClient
                .prompt(prompt)
                .options(OpenAiChatOptions.builder()
                        .temperature(0.3)
                        .maxTokens(1000))
                .stream()
                .content()
                .collectList() // 여러 조각을 리스트로 모으기
                .map(list -> String.join("", list)) // map(...) 데이터를 다른 형태로 변환하기, String.join("", list) 문자열 조각들을 붙이기
                .map(converter::convert); // 문자열을 Conversation 객체로 변환하기
    }

    // 벡터 저장소 — 저장 및 유사도 검색
    @Autowired
    private VectorStore vectorStore;

    public void addDocument() {
        List<Document> documents = List.of(
                new Document("대한민국의 수도는 서울이다.", Map.of("category", "geography", "year", 2020)),
                new Document("대통령 임기는 5년이다.", Map.of("category", "law", "year", 1987)),
                new Document("국회의원 임기는 4년이다.", Map.of("category", "law", "year", 1987))
        );
        
        vectorStore.add(documents); // 해당 문서들을 벡터 DB에 저장
        
    }

    // 유사도 검색 옵션 묶음
    public List<Document> searchDocument(String question) {
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question)
                        .topK(2)
                        .similarityThreshold(0.5)
                        .filterExpression("category == 'law'")
                        .build()
        );
    }
}
