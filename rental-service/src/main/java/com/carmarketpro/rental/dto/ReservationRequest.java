package com.carmarketpro.rental.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ReservationRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID carId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
