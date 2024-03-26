package com.atwaha.sis.service;

import com.atwaha.sis.components.Utils;
import com.atwaha.sis.model.dto.AuthResponse;
import com.atwaha.sis.model.dto.LoginRequest;
import com.atwaha.sis.model.dto.RegisterRequest;
import com.atwaha.sis.model.entities.User;
import com.atwaha.sis.repository.UserRepository;
import com.atwaha.sis.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Utils utils;

    public AuthResponse register(RegisterRequest request) {
        User newUser = modelMapper.map(request, User.class);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(newUser);
        String token = jwtService.generateToken(savedUser);

//        Persist the Token
        utils.saveUserToken(token, savedUser);

        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User authenticatedUser = userRepository.findByEmail(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(authenticatedUser);

        utils.revokeUserTokens(authenticatedUser);
        utils.saveUserToken(token, authenticatedUser);

        return AuthResponse.builder().token(token).build();
    }
}
