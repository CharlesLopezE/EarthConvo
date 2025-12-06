package edu.csula.cs3220stu12.earthconvo.repository;

import edu.csula.cs3220stu12.earthconvo.model.Sentence;
import edu.csula.cs3220stu12.earthconvo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface SentenceRepository extends JpaRepository<Sentence, Long> {
    List<Sentence> findByUser(User user);
    @Transactional
    void deleteByUserAndSentence(User user, String sentence);
}
