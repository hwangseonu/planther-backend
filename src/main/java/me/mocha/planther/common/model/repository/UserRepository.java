package me.mocha.planther.common.model.repository;

import me.mocha.planther.common.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsernameOrName(String username, String name);
    boolean existsByGradeAndClsAndNumber(int grade, int cls, int number);
}
