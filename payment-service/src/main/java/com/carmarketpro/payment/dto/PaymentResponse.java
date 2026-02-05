package com.carmarketpro.payment.dto;

import com.carmarketpro.payment.domain.PaymentStatus;
import com.carmarketpro.payment.domain.ReferenceType;
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
public class PaymentResponse {
    private UUID id;
    private UUID referenceId;
    private ReferenceType referenceType;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    private Instant createdAt;
}
