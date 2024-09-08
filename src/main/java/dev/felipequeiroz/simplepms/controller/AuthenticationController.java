package dev.felipequeiroz.simplepms.controller;

import dev.felipequeiroz.simplepms.domain.User;
import dev.felipequeiroz.simplepms.dto.AuthenticationDTO;
import dev.felipequeiroz.simplepms.dto.JWTTokenDTO;
import dev.felipequeiroz.simplepms.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Operation(
            summary = "Realiza o login do usuário",
            description = """
                    Permite que os usuários se autentiquem. Um Token JWT é retornado após uma autenticação bem sucedida e deve ser usado para acessar outros endpoints protegidos.
                    
                    username: admin | password: admin
                    """
    )
    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
            Authentication authentication = manager.authenticate(authenticationToken);
            var JWTToken = tokenService.generateToken(authentication.getPrincipal());
            return ResponseEntity.ok(new JWTTokenDTO(JWTToken));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
