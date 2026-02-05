package com.carmarketpro.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PurchaseRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID carId;

    @NotNull
    @DecimalMin("0")
    private BigDecimal amount;
}
