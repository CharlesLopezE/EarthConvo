package edu.csula.cs3220stu12.earthconvo.model;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class SavedLessons {

    final Map<String, List<String>> savedLessons = new HashMap<>();

    public List<String> getLessonsForUser(String username) {
        return savedLessons.computeIfAbsent(username, k -> new ArrayList<>());
    }

    public void savedLessons(String username, String lesson) {
        if (lesson == null || lesson.trim().isEmpty()) {
            return;
        }
        getLessonsForUser(username).add(lesson);
    }
}