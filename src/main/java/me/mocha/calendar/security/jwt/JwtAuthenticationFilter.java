package me.mocha.calendar.security.jwt;

import lombok.extern.slf4j.Slf4j;
import me.mocha.calendar.exception.UnprocessableEntityException;
import me.mocha.calendar.model.repository.UserRepository;
import me.mocha.calendar.security.UserDetialsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetialsServiceImpl userDetialsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            String header = request.getHeader("Authorization");
            String token = null;
            if (StringUtils.hasText(header) && header.startsWith("Bearer")) token = header.replaceFirst("Bearer", "").trim();
            if (StringUtils.hasText(token) && tokenProvider.validToken(token, JwtType.ACCESS)) {
                String username = tokenProvider.getUsernameFromToken(token);
                if (!userRepository.existsByUsername(username)) throw new UnprocessableEntityException("user not found");
                UserDetails userDetails = userDetialsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Could not set user authentication is security context - {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
