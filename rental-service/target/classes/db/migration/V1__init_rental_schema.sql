CREATE SCHEMA IF NOT EXISTS rental;

CREATE TABLE rental.reservations (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         UUID NOT NULL,
    car_id          UUID NOT NULL,
    start_date      DATE NOT NULL,
    end_date       DATE NOT NULL,
    total_amount   DECIMAL(12,2),
    status         VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED')),
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_reservations_user ON rental.reservations(user_id);
CREATE INDEX idx_reservations_car ON rental.reservations(car_id);
CREATE INDEX idx_reservations_status ON rental.reservations(status);
