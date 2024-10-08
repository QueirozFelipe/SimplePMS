package dev.felipequeiroz.simplepms.infra.security;

import dev.felipequeiroz.simplepms.repository.UserRepository;
import dev.felipequeiroz.simplepms.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var JWTToken = retrieveToken(request);
        String subject = null;
        if (JWTToken != null) {
            subject = tokenService.getSubject(JWTToken);
            var user = userRepository.findByUsername(subject);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String retrieveToken(HttpServletRequest request) {
        var autorizationHeader = request.getHeader("Authorization");
        if(autorizationHeader != null) {
            return autorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

}
