-- Création des utilisateurs et bases pour CarMarket Pro (dev) - Version idempotente
-- Vérifie l'existence avant de créer

-- ============================================
-- 1. CRÉATION DES UTILISATEURS (avec vérification)
-- ============================================
DO $$
BEGIN
    -- Utilisateur auth
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'auth') THEN
        CREATE USER auth WITH PASSWORD 'auth';
        RAISE NOTICE 'Utilisateur auth créé';
    ELSE
        RAISE NOTICE 'Utilisateur auth existe déjà';
    END IF;
    
    -- Utilisateur catalog
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'catalog') THEN
        CREATE USER catalog WITH PASSWORD 'catalog';
        RAISE NOTICE 'Utilisateur catalog créé';
    ELSE
        RAISE NOTICE 'Utilisateur catalog existe déjà';
    END IF;
    
    -- Utilisateur rental
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'rental') THEN
        CREATE USER rental WITH PASSWORD 'rental';
        RAISE NOTICE 'Utilisateur rental créé';
    ELSE
        RAISE NOTICE 'Utilisateur rental existe déjà';
    END IF;
    
    -- Utilisateur transaction
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'transaction') THEN
        CREATE USER transaction WITH PASSWORD 'transaction';
        RAISE NOTICE 'Utilisateur transaction créé';
    ELSE
        RAISE NOTICE 'Utilisateur transaction existe déjà';
    END IF;
    
    -- Utilisateur payment
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'payment') THEN
        CREATE USER payment WITH PASSWORD 'payment';
        RAISE NOTICE 'Utilisateur payment créé';
    ELSE
        RAISE NOTICE 'Utilisateur payment existe déjà';
    END IF;
    
    -- Utilisateur notification
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'notification') THEN
        CREATE USER notification WITH PASSWORD 'notification';
        RAISE NOTICE 'Utilisateur notification créé';
    ELSE
        RAISE NOTICE 'Utilisateur notification existe déjà';
    END IF;
    
    -- Utilisateur analytics
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'analytics') THEN
        CREATE USER analytics WITH PASSWORD 'analytics';
        RAISE NOTICE 'Utilisateur analytics créé';
    ELSE
        RAISE NOTICE 'Utilisateur analytics existe déjà';
    END IF;
END $$;

-- ============================================
-- 2. CRÉATION DES BASES DE DONNÉES
-- ============================================
-- Base auth_db
SELECT 
    CASE 
        WHEN EXISTS (SELECT FROM pg_database WHERE datname = 'auth_db') THEN 
            'Base auth_db existe déjà'
        ELSE 
            'CREATE DATABASE auth_db OWNER auth'
    END \gexec

-- Base catalog_db
SELECT 
    CASE 
        WHEN EXISTS (SELECT FROM pg_database WHERE datname = 'catalog_db') THEN 
            'Base catalog_db existe déjà'
        ELSE 
            'CREATE DATABASE catalog_db OWNER catalog'
    END \gexec

-- Base rental_db
SELECT 
    CASE 
        WHEN EXISTS (SELECT FROM pg_database WHERE datname = 'rental_db') THEN 
            'Base rental_db existe déjà'
        ELSE 
            'CREATE DATABASE rental_db OWNER rental'
    END \gexec

-- Base transaction_db
SELECT 
    CASE 
        WHEN EXISTS (SELECT FROM pg_database WHERE datname = 'transaction_db') THEN 
            'Base transaction_db existe déjà'
        ELSE 
            'CREATE DATABASE transaction_db OWNER transaction'
    END \gexec

-- Base payment_db
SELECT 
    CASE 
        WHEN EXISTS (SELECT FROM pg_database WHERE datname = 'payment_db') THEN 
            'Base payment_db existe déjà'
        ELSE 
            'CREATE DATABASE payment_db OWNER payment'
    END \gexec

-- Base notification_db
SELECT 
    CASE 
        WHEN EXISTS (SELECT FROM pg_database WHERE datname = 'notification_db') THEN 
            'Base notification_db existe déjà'
        ELSE 
            'CREATE DATABASE notification_db OWNER notification'
    END \gexec

-- Base analytics_db
SELECT 
    CASE 
        WHEN EXISTS (SELECT FROM pg_database WHERE datname = 'analytics_db') THEN 
            'Base analytics_db existe déjà'
        ELSE 
            'CREATE DATABASE analytics_db OWNER analytics'
    END \gexec

-- ============================================
-- 3. PRIVILÈGES SUPPLÉMENTAIRES
-- ============================================
-- Assure que les utilisateurs ont les droits sur leurs bases
GRANT ALL PRIVILEGES ON DATABASE auth_db TO auth;
GRANT ALL PRIVILEGES ON DATABASE catalog_db TO catalog;
GRANT ALL PRIVILEGES ON DATABASE rental_db TO rental;
GRANT ALL PRIVILEGES ON DATABASE transaction_db TO transaction;
GRANT ALL PRIVILEGES ON DATABASE payment_db TO payment;
GRANT ALL PRIVILEGES ON DATABASE notification_db TO notification;
GRANT ALL PRIVILEGES ON DATABASE analytics_db TO analytics;

-- Droit de création de schémas
ALTER USER auth CREATEDB;
ALTER USER catalog CREATEDB;
ALTER USER rental CREATEDB;
ALTER USER transaction CREATEDB;
ALTER USER payment CREATEDB;
ALTER USER notification CREATEDB;
ALTER USER analytics CREATEDB;

RAISE NOTICE 'Initialisation PostgreSQL terminée avec succès!';