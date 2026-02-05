package com.carmarketpro.transaction.dto;

import com.carmarketpro.transaction.domain.PurchaseStatus;
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
public class PurchaseResponse {
    private UUID id;
    private UUID userId;
    private UUID carId;
    private BigDecimal amount;
    private PurchaseStatus status;
    private UUID paymentId;
    private Instant createdAt;
}
