package dev.felipequeiroz.simplepms.controller;

import dev.felipequeiroz.simplepms.dto.AuthenticationDTO;
import dev.felipequeiroz.simplepms.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AuthenticationControllerTest {

    @Autowired
    private JacksonTester<AuthenticationDTO> jsonDto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenService tokenService;

    @Test
    @DisplayName("Deveria retornar código 200 e token JWT quando credenciais forem válidas")
    public void tentarLoginComDadosValidos() throws Exception {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("usernameValido", "passwordValido");
        var authenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());

        String tokenJWTEsperado = "tokenJWTEsperado";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authenticationToken);
        when(tokenService.generateToken(any())).thenReturn(tokenJWTEsperado);

        MockHttpServletResponse response = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDto.write(authenticationDTO).getJson())
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertEquals("{\"token\":\"" + tokenJWTEsperado + "\"}", response.getContentAsString());
    }

    @Test
    @DisplayName("Deveria retornar código 400 e não retornar um token JWT quando credenciais forem inválidas")
    public void tentarLoginComDadosInvalidos() throws Exception {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("usernameInvalido", "passwordInvalido");

        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        MockHttpServletResponse response = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDto.write(authenticationDTO).getJson())
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }




}