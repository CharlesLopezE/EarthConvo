package edu.csula.cs3220stu12.earthconvo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                You are EarthConvo, a tutoring AI.
                Keep responses short and simple.
                Use bullet points.
                Avoid markdown formatting.
                """)
                .build();
    }
}
