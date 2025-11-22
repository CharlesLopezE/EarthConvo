package edu.csula.cs3220stu12.earthconvo;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class SavedSentences {
    private final Map<String, List<String>> savedSentences = new HashMap<>();

    public List<String> getSentencesForUser(String username) {
        return savedSentences.computeIfAbsent(username, k -> new ArrayList<>());
    }

    public void savedSentences(String username, String sentences) {
        getSentencesForUser(username).add(sentences);
    }
}