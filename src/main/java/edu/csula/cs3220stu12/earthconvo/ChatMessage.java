package edu.csula.cs3220stu12.earthconvo;

public class ChatMessage {

    public static final String LESSON = "lesson";
    public static final String SENTENCE = "sentence";
    public static final String VOCAB = "vocab";

    private final String userMessage;
    private final String aiResponse;
    private final String aiSummary;
    public final String type;

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
