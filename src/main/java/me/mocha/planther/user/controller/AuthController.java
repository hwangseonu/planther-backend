package me.mocha.planther.user.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.planther.common.exception.NotFoundException;
import me.mocha.planther.common.model.entity.User;
import me.mocha.planther.common.model.repository.UserRepository;
import me.mocha.planther.user.request.SignInRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public void signIn(@Valid @RequestBody SignInRequest request) {
        User user = userRepository.findById(request.getUsername()).orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
    }

}
