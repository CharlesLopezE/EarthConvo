package edu.csula.cs3220stu12.earthconvo;

public class ChatMessage {
    private final String userMessage;
    private final String aiResponse;
    private final String aiSummary;
    public final String type;  // "lesson", "sentence", "vocab"

    public ChatMessage(String userMessage, String aiResponse, String aiSummary, String type) {
        this.userMessage = userMessage;
        this.aiResponse = aiResponse;
        this.aiSummary = aiSummary;
        this.type = type;
    }

    public String getUserMessage() { return userMessage; }
    public String getAiResponse() { return aiResponse; }
    public String getAiSummary() { return aiSummary; }
    public String getType() { return type; }
}

