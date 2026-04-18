package com.krasnovozBek.krasnovozBek.service;

import com.krasnovozBek.krasnovozBek.dto.LoginRequest;
import com.krasnovozBek.krasnovozBek.domain.User;
import com.krasnovozBek.krasnovozBek.dao.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository repo,
                       PasswordEncoder encoder,
                       JwtService jwtService) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public Map<String, String> login(LoginRequest request) {
        User user = repo.findByUsername(request.username).orElseThrow(() -> new RuntimeException("Користувача не знайдено"));

        if (!encoder.matches(request.password, user.getPassword())) {
            throw new RuntimeException("Невірний пароль");
        }

        String token = jwtService.generateToken(user);

        return Map.of(
                "token", token,
                "role", user.getRole()
        );
    }
}