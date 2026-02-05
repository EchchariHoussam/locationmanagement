package com.carmarketpro.auth.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class UserResponse {

    private UUID id;
    private String email;
    private boolean enabled;
    private List<String> roles;
    private Instant createdAt;

    public UserResponse() {
    }

    public UserResponse(UUID id, String email, boolean enabled, List<String> roles, Instant createdAt) {
        this.id = id;
        this.email = email;
        this.enabled = enabled;
        this.roles = roles;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public static UserResponseBuilder builder() {
        return new UserResponseBuilder();
    }

    public static final class UserResponseBuilder {
        private UUID id;
        private String email;
        private boolean enabled;
        private List<String> roles;
        private Instant createdAt;

        private UserResponseBuilder() {
        }

        public UserResponseBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserResponseBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserResponseBuilder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public UserResponseBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(id, email, enabled, roles, createdAt);
        }
    }
}
