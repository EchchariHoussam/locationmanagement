package com.carmarketpro.auth.service;

import com.carmarketpro.auth.domain.User;
import com.carmarketpro.auth.dto.UserResponse;
import com.carmarketpro.auth.dto.UserUpdateRequest;
import com.carmarketpro.auth.exception.ResourceNotFoundException;
import com.carmarketpro.auth.mapper.UserMapper;
import com.carmarketpro.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // 🔹 Trouver un utilisateur par ID
    public UserResponse findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return userMapper.toResponse(user);
    }

    // 🔹 Mettre à jour les infos de l'utilisateur (sauf role)
    @Transactional
public UserResponse update(UUID id, UserUpdateRequest request) {

    User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", id));

    if (request.getEnabled() != null) {
        user.setEnabled(request.getEnabled());
    }

    if (request.getEmail() != null && !request.getEmail().isBlank()) {
        user.setEmail(request.getEmail());
    }


    if (request.getPassword() != null && !request.getPassword().isBlank()) {
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
    }

    // ❌ ROLE jamais modifié

    return userMapper.toResponse(userRepository.save(user));
}


    // 🔹 Liste paginée de tous les utilisateurs
    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

}
