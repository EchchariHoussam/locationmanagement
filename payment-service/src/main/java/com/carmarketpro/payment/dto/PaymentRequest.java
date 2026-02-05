package com.carmarketpro.payment.dto;

import com.carmarketpro.payment.domain.ReferenceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRequest {

    @NotNull
    private UUID referenceId;

    @NotNull
    private ReferenceType referenceType;

    @NotNull
    @DecimalMin("0")
    private BigDecimal amount;

    private String currency = "EUR";
}
