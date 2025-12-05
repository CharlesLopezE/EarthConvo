package edu.csula.cs3220stu12.earthconvo;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class SavedSentences {
    private final Map<String, List<String>> savedSentences = new HashMap<>();

    public List<String> getSentencesForUser(String username) {
        return savedSentences.computeIfAbsent(username, k -> new ArrayList<>());
    }

    /**
     * Store a sentence in the format "original||translation".
     * If the line does not contain a clear separator, the whole line
     * is stored as the original and the translation is left empty.
     */
    public void savedSentences(String username, String sentenceLine) {
        if (sentenceLine == null || sentenceLine.trim().isEmpty()) {
            return;
        }
        String combined = normalize(sentenceLine);
        getSentencesForUser(username).add(combined);
    }

    public void deleteSentence(String userEmail, String sentenceEntry) {
        List<String> list = savedSentences.get(userEmail);
        if (list != null) {
            list.remove(sentenceEntry);
        }
    }

    private String normalize(String raw) {
        String s = raw.trim();
        if (s.startsWith("-")) {
            s = s.substring(1).trim(); // remove leading "- "
        }

        String original = s;
        String translation = "";

        String[] separators = {" -> ", " → ", " — ", " - ", " : ", " = "};
        for (String sep : separators) {
            int idx = s.indexOf(sep);
            if (idx != -1) {
                original = s.substring(0, idx).trim();
                translation = s.substring(idx + sep.length()).trim();
                break;
            }
        }

        return original +  translation;
    }
}
