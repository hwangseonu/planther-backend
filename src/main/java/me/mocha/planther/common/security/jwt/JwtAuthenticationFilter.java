package me.mocha.planther.common.security.jwt;

import lombok.extern.slf4j.Slf4j;
import me.mocha.planther.common.exception.NotFoundException;
import me.mocha.planther.common.exception.UnprocessableEntityException;
import me.mocha.planther.common.model.repository.UserRepository;
import me.mocha.planther.common.security.user.UserDetailsServiceImpl;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserRepository userRepository, UserDetailsServiceImpl userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            String token = getTokenFromRequestHeader(request);
            if (StringUtils.hasText(token)) {
                if (jwtProvider.validToken(token, JwtType.ACCESS)) {
                    String username = jwtProvider.getUsernameFromToken(token);
                    if (!userRepository.existsById(username)) {
                        throw new NotFoundException("존재하지 않는 사용자입니다.");
                    }
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new UnprocessableEntityException("unprocessable token");
                }
            }
        } catch (Exception e) {
            log.error("Could not set user authentication is security context - {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequestHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer")) {
            return header.replaceFirst("Bearer", "").trim();
        }
        return "";
    }

}
