package edu.csula.cs3220stu12.earthconvo;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class SavedVocab {
    private final Map<String, List<String>> savedVocab = new HashMap<>();

    public List<String> getVocabForUser(String username) {
        return savedVocab.computeIfAbsent(username, k -> new ArrayList<>());
    }

    public void savedVocab(String username, String vocabWord) {
        if (vocabWord == null || vocabWord.trim().isEmpty()) {
            return;
        }
        getVocabForUser(username).add(vocabWord);
    }
}
