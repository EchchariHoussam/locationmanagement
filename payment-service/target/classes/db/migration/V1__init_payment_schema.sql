CREATE SCHEMA IF NOT EXISTS payment;

CREATE TABLE payment.payments (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    reference_id    UUID NOT NULL,
    reference_type  VARCHAR(20) NOT NULL CHECK (reference_type IN ('PURCHASE', 'RENTAL')),
    amount          DECIMAL(12,2) NOT NULL,
    currency        VARCHAR(3) DEFAULT 'EUR',
    status          VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'SUCCESS', 'FAILED')),
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_payments_reference ON payment.payments(reference_id);
CREATE INDEX idx_payments_status ON payment.payments(status);
