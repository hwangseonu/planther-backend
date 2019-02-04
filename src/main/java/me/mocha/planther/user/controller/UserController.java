package me.mocha.planther.user.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.planther.common.annotations.CurrentUser;
import me.mocha.planther.common.model.entity.User;
import me.mocha.planther.common.model.repository.UserRepository;
import me.mocha.planther.user.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<User> signUp(@Valid @RequestBody SignUpRequest request) {
        if (userRepository.existsById(request.getName()) || userRepository.existsByGradeAndClsAndNumber(request.getGrade(), request.getCls(), request.getNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userRepository.save(User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .grade(request.getGrade())
                .cls(request.getCls())
                .number(request.getNumber())
                .role("ROLE_USER")
                .build());
        log.info("signed up {}", user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<User> getUser(@CurrentUser User user) {
        return ResponseEntity.ok(user);
    }

}
