package edu.csula.cs3220stu12.earthconvo.repository;

import edu.csula.cs3220stu12.earthconvo.model.Vocab;
import edu.csula.cs3220stu12.earthconvo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface VocabRepository extends JpaRepository<Vocab, Long> {
    List<Vocab> findByUser(User user);
    @Transactional
    void deleteByUserAndEntry(User user, String entry);
}
