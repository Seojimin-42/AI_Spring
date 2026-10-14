package com.example.demo.service;

import com.example.demo.dto.Hotel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AIService2 {
    private ChatClient chatClient;

    public AIService2(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    // list 저수준 구조화 출력
    public List<String> listOutput1(String city) {
        ListOutputConverter converter = new ListOutputConverter();

        PromptTemplate promptTemplate = PromptTemplate.builder()
                .template("{city}에서 유명한 호텔 목록 5개를 출력해줘. {format}")
                .build();

        Prompt prompt = promptTemplate.create(
                Map.of("city", city, "format", converter.getFormat())
        );

        String result = chatClient
                .prompt(prompt)
                .call()
                .content();

        List<String> list = converter.convert(result);

        return list;
    }

    // list 고수준 구조화 출력
    public List<String> listOutput2(String city) {
        return chatClient
                .prompt()
                .user("%s에서 유명한 호텔 목록 5개를 출력해줘".formatted(city))
                .call()
                .entity(new ListOutputConverter());
    }

    // bean 저수준 구조화 출력
    public Hotel beanOutput1(String city) {
        BeanOutputConverter<Hotel> converter = new BeanOutputConverter(Hotel.class);

        PromptTemplate promptTemplate = PromptTemplate.builder()
                .template("{city}에서 유명한 호텔 목록 5개를 출력해줘. {format}")
                .build();

        Prompt prompt = promptTemplate.create(
                Map.of("city", city, "format", converter.getFormat())
        );

        String result = chatClient
                .prompt(prompt)
                .call()
                .content();

        Hotel hotel = converter.convert(result);

        return hotel;
    }

    // bean 고수준 구조화 출력
    public Hotel beanOutput2(String city) {
        return chatClient
                .prompt()
                .user("%s에서 유명한 호텔 목록 5개를 출력해줘".formatted(city))
                .call()
                .entity(Hotel.class); // entity 안에 객체 생성을 안함.
    }

    // beanList 저수준 구조화 출력
    public List<Hotel> beanListOutput1(String cities) {
        BeanOutputConverter<List<Hotel>> converter =
                new BeanOutputConverter<>(new ParameterizedTypeReference<List<Hotel>>() {});

        PromptTemplate promptTemplate = PromptTemplate.builder()
                .template("다음 도시들에서 유명한 호텔 3개를 출력해줘. {cities} {format}")
                .build();

        Prompt prompt = promptTemplate.create(
                Map.of("cities", cities, "format", converter.getFormat())
        );

        String result = chatClient
                .prompt(prompt)
                .call()
                .content();

        List<Hotel> hotelList = converter.convert(result);

        return hotelList;
    }

    // beanList 고수준 구조화 출력
    public List<Hotel> beanListOutput2(String cities) {
        return chatClient
                .prompt()
                .user("다음 도시들에서 유명한 호텔 3개를 출력해줘. %s".formatted(cities))
                .call()
                .entity(new ParameterizedTypeReference<List<Hotel>>() {});
    }

    // map 저수준 구조화 출력
    public Map<String, Object> mapOutput1(String hotel) {
        MapOutputConverter mapOutputConverter = new MapOutputConverter();

        PromptTemplate promptTemplate = new PromptTemplate("호텔 {hotel}에 대해 정보를 알려줘. {format}");

        Prompt prompt = promptTemplate.create(
                Map.of("hotel", hotel, "format", mapOutputConverter.getFormat())
        );

        String result = chatClient
                .prompt(prompt)
                .call()
                .content();

        Map<String, Object> hotelInfo = mapOutputConverter.convert(result);

        return hotelInfo;
    }

    // map 고수준 구조화 출력
    public Map<String, Object> mapOutput2(String hotel) {
        return chatClient
                .prompt()
                .user("호텔 %s에 대해 정보를 알려줘".formatted(hotel))
                .call()
                .entity(new MapOutputConverter());
    }
}
