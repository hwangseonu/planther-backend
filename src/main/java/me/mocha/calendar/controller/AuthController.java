package me.mocha.calendar.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.calendar.exception.BadRequestException;
import me.mocha.calendar.exception.IncorrectPasswordException;
import me.mocha.calendar.exception.NotFoundException;
import me.mocha.calendar.model.entity.User;
import me.mocha.calendar.model.repository.UserRepository;
import me.mocha.calendar.payload.request.auth.SignInRequest;
import me.mocha.calendar.payload.response.auth.SignInResponse;
import me.mocha.calendar.security.jwt.JwtTokenProvider;
import me.mocha.calendar.security.jwt.JwtType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping
    public SignInResponse signIn(@Valid @RequestBody SignInRequest request) throws NotFoundException {
        User found = userRepository.findById(request.getUsername()).orElseThrow(() -> new NotFoundException("Could not find user"));
        if (!passwordEncoder.matches(request.getPassword(), found.getPassword())) throw new IncorrectPasswordException("password incorrect");
        return new SignInResponse(tokenProvider.generateToken(found.getUsername(), JwtType.ACCESS), tokenProvider.generateToken(found.getUsername(), JwtType.REFRESH));
    }

    @GetMapping("/refresh")
    public SignInResponse refresh(@RequestHeader("Authorization") String header) throws BadRequestException{
        if (header.startsWith("Bearer")) {
            String token = header.replaceFirst("Bearer", "").trim();
            if (StringUtils.hasText(token) && tokenProvider.validToken(token, JwtType.REFRESH)) {
                String username = tokenProvider.getUsernameFromToken(token);
                if (userRepository.existsByUsername(username)) {
                    SignInResponse response = new SignInResponse(tokenProvider.generateToken(username, JwtType.ACCESS), null);
                    if (ChronoUnit.DAYS.between(LocalDate.now(), tokenProvider.getExpiration(token)) <= 7) response.setRefreshToken(tokenProvider.generateToken(username, JwtType.REFRESH));
                    return response;
                }
            }
        }
        throw new BadRequestException("need refresh token");
    }

}
