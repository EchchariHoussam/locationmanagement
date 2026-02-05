package com.carmarketpro.prediction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PricePredictionResponse {
    private BigDecimal predictedPrice;
    private String currency;
    private String modelVersion;  // e.g. "v1.0"
}
