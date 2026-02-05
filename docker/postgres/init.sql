-- Création des utilisateurs et bases pour CarMarket Pro (dev)
CREATE USER auth WITH PASSWORD 'auth';
CREATE USER catalog WITH PASSWORD 'catalog';
CREATE USER rental WITH PASSWORD 'rental';
CREATE USER transaction WITH PASSWORD 'transaction';
CREATE USER payment WITH PASSWORD 'payment';
CREATE USER notification WITH PASSWORD 'notification';
CREATE USER analytics WITH PASSWORD 'analytics';

CREATE DATABASE auth_db OWNER auth;
CREATE DATABASE catalog_db OWNER catalog;
CREATE DATABASE rental_db OWNER rental;
CREATE DATABASE transaction_db OWNER transaction;
CREATE DATABASE payment_db OWNER payment;
CREATE DATABASE notification_db OWNER notification;
CREATE DATABASE analytics_db OWNER analytics;
