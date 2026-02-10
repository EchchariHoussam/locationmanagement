-- ============================================
-- Migration V1: Initialisation du schéma d'authentification
-- Ne pas créer de DATABASE ici - déjà fait par Docker
-- ============================================

-- ============================================
-- 1. CRÉATION DES TABLES (avec vérification)
-- ============================================

-- Table users
CREATE TABLE IF NOT EXISTS users (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email           VARCHAR(255) NOT NULL UNIQUE,
    password_hash   VARCHAR(255) NOT NULL,
    enabled         BOOLEAN NOT NULL DEFAULT true,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE users IS 'Table des utilisateurs du système';
COMMENT ON COLUMN users.email IS 'Email unique de l''utilisateur';
COMMENT ON COLUMN users.password_hash IS 'Hash du mot de passe';
COMMENT ON COLUMN users.enabled IS 'Indique si le compte est activé';

-- Table roles
CREATE TABLE IF NOT EXISTS roles (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name            VARCHAR(50) NOT NULL UNIQUE,
    description     VARCHAR(255),
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE roles IS 'Table des rôles disponibles';
COMMENT ON COLUMN roles.name IS 'Nom unique du rôle (USER, ADMIN, AGENCY)';
COMMENT ON COLUMN roles.description IS 'Description du rôle';

-- Table user_roles (relation many-to-many)
CREATE TABLE IF NOT EXISTS user_roles (
    user_id         UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id         UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    assigned_at     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id)
);

COMMENT ON TABLE user_roles IS 'Table de liaison utilisateurs-rôles';
COMMENT ON COLUMN user_roles.assigned_at IS 'Date d''assignation du rôle';

-- Table refresh_tokens
CREATE TABLE IF NOT EXISTS refresh_tokens (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token           VARCHAR(512) NOT NULL UNIQUE,
    expires_at      TIMESTAMP WITH TIME ZONE NOT NULL,
    revoked         BOOLEAN NOT NULL DEFAULT false,
    revoked_at      TIMESTAMP WITH TIME ZONE,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE refresh_tokens IS 'Table des tokens de rafraîchissement';
COMMENT ON COLUMN refresh_tokens.token IS 'Token JWT de rafraîchissement';
COMMENT ON COLUMN refresh_tokens.expires_at IS 'Date d''expiration du token';
COMMENT ON COLUMN refresh_tokens.revoked IS 'Indique si le token est révoqué';

-- ============================================
-- 2. INDEX POUR LES PERFORMANCES
-- ============================================
-- Index pour users
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_enabled ON users(enabled) WHERE enabled = true;

-- Index pour refresh_tokens
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_token ON refresh_tokens(token);
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_user_id ON refresh_tokens(user_id);
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_revoked ON refresh_tokens(revoked) WHERE revoked = false;
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_expires ON refresh_tokens(expires_at);

-- Index pour user_roles
CREATE INDEX IF NOT EXISTS idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX IF NOT EXISTS idx_user_roles_role_id ON user_roles(role_id);

-- Index pour roles
CREATE INDEX IF NOT EXISTS idx_roles_name ON roles(name);

-- ============================================
-- 3. DONNÉES INITIALES (SEED)
-- ============================================
-- Insertion des rôles de base (uniquement s'ils n'existent pas)
INSERT INTO roles (id, name, description)
SELECT 
    gen_random_uuid(), 
    'USER', 
    'Utilisateur standard du système'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'USER');

INSERT INTO roles (id, name, description)
SELECT 
    gen_random_uuid(), 
    'ADMIN', 
    'Administrateur système avec tous les droits'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN');

INSERT INTO roles (id, name, description)
SELECT 
    gen_random_uuid(), 
    'AGENCY', 
    'Agence de location de voitures'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'AGENCY');

-- ============================================
-- 4. TRIGGERS POUR MISES À JOUR AUTOMATIQUES
-- ============================================
-- Trigger pour updated_at automatique sur users
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

DROP TRIGGER IF EXISTS update_users_updated_at ON users;
CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- ============================================
-- 5. VUES UTILES (OPTIONNEL)
-- ============================================
-- Vue pour voir les utilisateurs avec leurs rôles
CREATE OR REPLACE VIEW user_with_roles AS
SELECT 
    u.id,
    u.email,
    u.enabled,
    u.created_at,
    u.updated_at,
    array_agg(r.name) as roles,
    COUNT(rt.id) as active_refresh_tokens
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.id
LEFT JOIN refresh_tokens rt ON u.id = rt.user_id AND rt.revoked = false AND rt.expires_at > CURRENT_TIMESTAMP
GROUP BY u.id, u.email, u.enabled, u.created_at, u.updated_at;

COMMENT ON VIEW user_with_roles IS 'Vue agrégée des utilisateurs avec leurs rôles et tokens actifs';

-- ============================================
-- 6. DROITS D'ACCÈS POUR L'UTILISATEUR 'auth'
-- ============================================
-- Assure que l'utilisateur 'auth' a tous les droits sur ces tables
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO auth;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO auth;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO auth;

-- Pour les futures tables
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO auth;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO auth;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON FUNCTIONS TO auth;

RAISE NOTICE 'Migration V1 terminée avec succès!';