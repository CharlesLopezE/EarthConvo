package edu.csula.cs3220stu12.earthconvo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        // Uses your spring.ai.* properties from application.properties
        return builder.build();
    }
}
