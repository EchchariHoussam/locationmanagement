-- Catalog service schema
-- Using public schema

CREATE TABLE public.cars (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    brand           VARCHAR(100) NOT NULL,
    model           VARCHAR(100) NOT NULL,
    year            INT NOT NULL,
    price_sale      DECIMAL(12,2),
    price_rent_day  DECIMAL(10,2),
    available_sale  BOOLEAN DEFAULT true,
    available_rent  BOOLEAN DEFAULT true,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_cars_brand ON public.cars(brand);
CREATE INDEX idx_cars_year ON public.cars(year);
CREATE INDEX idx_cars_available ON public.cars(available_sale, available_rent);
