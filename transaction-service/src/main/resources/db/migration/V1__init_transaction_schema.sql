CREATE SCHEMA IF NOT EXISTS transaction;

CREATE TABLE transaction.purchases (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         UUID NOT NULL,
    car_id          UUID NOT NULL,
    amount          DECIMAL(12,2) NOT NULL,
    status          VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'PAID', 'CANCELLED')),
    payment_id      UUID,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_purchases_user ON transaction.purchases(user_id);
CREATE INDEX idx_purchases_car ON transaction.purchases(car_id);
CREATE INDEX idx_purchases_status ON transaction.purchases(status);
