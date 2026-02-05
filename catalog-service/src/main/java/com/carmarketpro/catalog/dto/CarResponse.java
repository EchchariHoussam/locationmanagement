package com.carmarketpro.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {
    private UUID id;
    private String brand;
    private String model;
    private Integer year;
    private BigDecimal priceSale;
    private BigDecimal priceRentDay;
    private boolean availableSale;
    private boolean availableRent;
    private Instant createdAt;
}
