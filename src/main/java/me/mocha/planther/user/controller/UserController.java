package me.mocha.planther.user.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.planther.common.model.entity.User;
import me.mocha.planther.common.model.repository.UserRepository;
import me.mocha.planther.user.request.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User signUp(@Valid @RequestBody SignUpRequest request) {
        User user = userRepository.save(User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .grade(request.getGrade())
                .cls(request.getCls())
                .number(request.getNumber())
                .build());
        log.info("signed up {}", user.getUsername());
        return user;
    }

}
