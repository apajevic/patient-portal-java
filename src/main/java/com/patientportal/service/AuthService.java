package com.patientportal.service;

import com.patientportal.dto.LoginDTO;
import com.patientportal.dto.RegisterDTO;
import com.patientportal.interfaces.IAuthService;
import com.patientportal.model.User;
import com.patientportal.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.HashMap;
import java.util.Map;

public class AuthService implements IAuthService {
    @Inject
    UserRepository userRepository;

    @Inject
    PasswordService passwordService;
    @Override
    public String login(LoginDTO loginDTO) {
        User user = userRepository.find("email", loginDTO.email())
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException("User with email " + loginDTO.email() + " not found"));

        if (!passwordService.validatePassword(loginDTO.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        Map<String, String> response = new HashMap<>();
        response.put("status:", "User logged in successfully");
        response.put("id:", user.getId().toString());
        response.put("token:", null);

        return null;
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        return null;
    }
}
