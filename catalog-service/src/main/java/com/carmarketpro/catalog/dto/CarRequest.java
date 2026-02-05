package com.carmarketpro.catalog.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarRequest {

    @NotBlank(message = "Brand is required")
    @Size(max = 100)
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(max = 100)
    private String model;

    @NotNull
    @Min(1900)
    @Max(2100)
    private Integer year;

    @DecimalMin("0")
    private BigDecimal priceSale;

    @DecimalMin("0")
    private BigDecimal priceRentDay;

    private Boolean availableSale = true;
    private Boolean availableRent = true;
}
