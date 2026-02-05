package com.carmarketpro.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeResponse {
    private UUID id;
    private String email;
    private boolean enabled;
    private List<String> roles;
    private Instant createdAt;
}
