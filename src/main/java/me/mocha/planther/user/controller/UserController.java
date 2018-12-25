package me.mocha.planther.user.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.planther.common.exception.ConflictException;
import me.mocha.planther.common.model.entity.User;
import me.mocha.planther.common.model.repository.UserRepository;
import me.mocha.planther.user.request.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User signUp(@Valid @RequestBody SignUpRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .grade(request.getGrade())
                .cls(request.getCls())
                .number(request.getNumber())
                .build();
        if (userRepository.existsByUsernameOrName(user.getUsername(), user.getName())
                || userRepository.existsByGradeAndClsAndNumber(user.getGrade(), user.getCls(), user.getNumber())) {
            throw new ConflictException("이미 존재하는 사용자정보입니다.");
        }
        user = userRepository.save(user);
        log.info("signed up {}", user.getUsername());
        return user;
    }

}
