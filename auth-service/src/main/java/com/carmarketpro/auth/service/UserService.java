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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return userMapper.toResponse(user);
    }

   @Transactional
public UserResponse update(UUID id, UserUpdateRequest request) {

    User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", id));

    // ENABLED
    if (request.getEnabled() != null) {
        user.setEnabled(request.getEnabled());
    }

    // EMAIL
    if (request.getEmail() != null && !request.getEmail().isBlank()) {
        user.setEmail(request.getEmail());
    }

    // PASSWORD → utiliser le bon champ
    if (request.getPassword() != null && !request.getPassword().isBlank()) {
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
    }

    // ❌ ROLE jamais modifié

    return userMapper.toResponse(userRepository.save(user));
}
}