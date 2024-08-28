package dev.felipequeiroz.simplepms.controller;

import dev.felipequeiroz.simplepms.domain.User;
import dev.felipequeiroz.simplepms.dto.AuthenticationDTO;
import dev.felipequeiroz.simplepms.dto.JWTTokenDTO;
import dev.felipequeiroz.simplepms.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
            var authentication = manager.authenticate(authenticationToken);
            var JWTToken = tokenService.generateToken((User)authentication.getPrincipal());
            return ResponseEntity.ok(new JWTTokenDTO(JWTToken));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
