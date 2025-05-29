package com.example.TTS_web.backend.service;

import com.example.TTS_web.backend.dto.UserDTO;
import com.example.TTS_web.backend.entity.User;
import com.example.TTS_web.backend.enumerate.Role;
import com.example.TTS_web.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(UserDTO user) {
        User found = userRepository.findByUsername(user.getUsername()).orElse(null);
        if(found != null) return;
        User new_user = new User();
        new_user.setUsername(user.getUsername());
        if (user.getUsername().equals("ADMIN")) {
            new_user.setRole(Role.ADMIN);
        } else {
            new_user.setRole(Role.USER);
        }
        new_user.setPassword(passwordEncoder.encode(user.getPassword()));
        new_user.setExpired(false);
        userRepository.save(new_user);
    }
}
