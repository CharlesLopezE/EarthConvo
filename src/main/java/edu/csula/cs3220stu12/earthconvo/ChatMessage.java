package edu.csula.cs3220stu12.earthconvo;

public class ChatMessage {
    public final String userMessage;
    private final String aiResponse;
    private final String aiSummary;

    public ChatMessage(String userMessage, String aiResponse, String aiSummary) {
        this.userMessage = userMessage;
        this.aiResponse = aiResponse;
        this.aiSummary = aiSummary;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getAiResponse() {
        return aiResponse;
    }

    public String getAiSummary() {
        return aiSummary;
    }
}
