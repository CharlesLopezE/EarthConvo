package edu.csula.cs3220stu12.earthconvo.repository;

import edu.csula.cs3220stu12.earthconvo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    // Spring Data JPA provides findById, save, delete, etc.
}
