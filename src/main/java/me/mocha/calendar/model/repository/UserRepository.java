package me.mocha.calendar.model.repository;

import me.mocha.calendar.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByGradeAndClsAndNumber(Integer grade, Integer cls, Integer number);
}
