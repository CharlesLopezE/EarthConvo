package edu.csula.cs3220stu12.earthconvo;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class SavedLessons {
    private final Map<String, List<String>> savedLessons = new HashMap<>();

    public List<String> getLessonsForUser(String username) {
        return savedLessons.computeIfAbsent(username, k -> new ArrayList<>());
    }

    public void savedLessons(String username, String joke) {
        getLessonsForUser(username).add(joke);
    }
}
