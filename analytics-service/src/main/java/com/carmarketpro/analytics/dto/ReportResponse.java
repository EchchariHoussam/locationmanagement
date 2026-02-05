package com.carmarketpro.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private UUID id;
    private String reportType;
    private LocalDate periodFrom;
    private LocalDate periodTo;
    private Map<String, Object> payload;
    private Instant createdAt;
}
