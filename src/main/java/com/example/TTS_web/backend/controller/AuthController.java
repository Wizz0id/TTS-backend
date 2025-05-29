package com.example.TTS_web.backend.controller;

import com.example.TTS_web.backend.dto.UserDTO;
import com.example.TTS_web.backend.service.AuthService;
import com.example.TTS_web.backend.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserDetailsServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO dto) {
        String username = dto.getUsername();
        String role = "ROLE_" + userService.loadUserByUsername(username).getRole();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username, null, AuthorityUtils.createAuthorityList(role)
        );

        SecurityContextHolder.getContext().setAuthentication(token);
        Map<String, Object> response = new HashMap<>();
        response.put("role", role);
        response.put("message", "Вы успешно вошли");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO dto) {
        UserDTO newUser = new UserDTO();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setRole("USER");

        authService.saveUser(newUser);

        Map<String, Object> response = new HashMap<>();
        response.put("role", "USER");
        response.put("message", "Регистрация успешна");
        return ResponseEntity.ok(response);
    }
}
