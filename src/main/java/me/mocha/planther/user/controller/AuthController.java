package me.mocha.planther.user.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.planther.common.model.entity.User;
import me.mocha.planther.common.model.repository.UserRepository;
import me.mocha.planther.common.security.jwt.JwtProvider;
import me.mocha.planther.common.security.jwt.JwtType;
import me.mocha.planther.user.request.SignInRequest;
import me.mocha.planther.user.response.SignInResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {
        User user = userRepository.findById(request.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.info("{} is authenticated", user.getUsername());
        return ResponseEntity.ok(new SignInResponse(
                jwtProvider.generateToken(user.getUsername(), JwtType.ACCESS),
                jwtProvider.generateToken(user.getUsername(), JwtType.REFRESH)));
    }

}
