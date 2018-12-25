package me.mocha.planther.user.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.planther.common.exception.NotFoundException;
import me.mocha.planther.common.exception.UnauthorizedException;
import me.mocha.planther.common.model.entity.User;
import me.mocha.planther.common.model.repository.UserRepository;
import me.mocha.planther.common.security.jwt.JwtProvider;
import me.mocha.planther.common.security.jwt.JwtType;
import me.mocha.planther.user.request.SignInRequest;
import me.mocha.planther.user.response.SignInResponse;
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
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public SignInResponse signIn(@Valid @RequestBody SignInRequest request) {
        User user = userRepository.findById(request.getUsername()).orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("비밀번호가 맞지 않습니다.");
        }
        return new SignInResponse(
                jwtProvider.generateToken(user.getUsername(), JwtType.ACCESS),
                jwtProvider.generateToken(user.getUsername(), JwtType.REFRESH));
    }

}
