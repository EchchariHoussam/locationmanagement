package com.carmarketpro.rental.dto;

import com.carmarketpro.rental.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private UUID id;
    private UUID userId;
    private UUID carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalAmount;
    private ReservationStatus status;
    private Instant createdAt;
}
