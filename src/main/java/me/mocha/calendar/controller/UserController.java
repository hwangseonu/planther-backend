package me.mocha.calendar.controller;

import me.mocha.calendar.exception.ConflictException;
import me.mocha.calendar.model.entity.User;
import me.mocha.calendar.model.repository.UserRepository;
import me.mocha.calendar.payload.request.user.SignUpRequest;
import me.mocha.calendar.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User signUp(@Valid @RequestBody SignUpRequest request) throws ConflictException {
        if (userRepository.existsByUsername(request.getUsername()) || userRepository.existsByGradeAndClsAndNumber(request.getGrade(), request.getCls(), request.getNumber()))
            throw new ConflictException("conflict");
        return userRepository.save(User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .grade(request.getGrade())
                .cls(request.getCls())
                .number(request.getNumber())
                .role("ROLE_USER")
                .build());
    }

    @GetMapping
    public User info(@CurrentUser User user) {
        return user;
    }

}
