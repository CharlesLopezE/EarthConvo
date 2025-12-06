package edu.csula.cs3220stu12.earthconvo.repository;

import edu.csula.cs3220stu12.earthconvo.model.Lesson;
import edu.csula.cs3220stu12.earthconvo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    // Fetch all lessons for a user
    List<Lesson> findByUser(User user);

    // Optional: delete by user + content (not recommended if duplicates possible)
    void deleteByUserAndContent(User user, String content);

    // The standard deleteById(Long id) from JpaRepository is sufficient for most cases
}
