package edu.csula.cs3220stu12.earthconvo;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class SavedSentences {
    private final Map<String, List<String>> savedSentences = new HashMap<>();

    public List<String> getSentencesForUser(String username) {
        return savedSentences.computeIfAbsent(username, k -> new ArrayList<>());
    }

    public void savedSentences(String username, String sentence) {
        if (sentence == null || sentence.trim().isEmpty()) {
            return;
        }
        getSentencesForUser(username).add(sentence);
    }
    public void deleteSentence(String userEmail, String sentence) {
        List<String> list = savedSentences.get(userEmail);
        if (list != null) {
            list.remove(sentence);
        }
    }
}