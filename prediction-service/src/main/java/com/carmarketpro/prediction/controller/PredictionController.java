package com.carmarketpro.prediction.controller;

import com.carmarketpro.prediction.dto.PricePredictionRequest;
import com.carmarketpro.prediction.dto.PricePredictionResponse;
import com.carmarketpro.prediction.service.PredictionService;
import com.carmarketpro.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor
@Tag(name = "Predictions", description = "Prédiction intelligente des prix (ML)")
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping("/price")
    @Operation(summary = "Predict car price", description = "Prédiction via modèle ML (ou heuristique si ML non configuré)")
    public ResponseEntity<ApiResponse<PricePredictionResponse>> predictPrice(@Valid @RequestBody PricePredictionRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(predictionService.predictPrice(request)));
    }

    @GetMapping("/price")
    @Operation(summary = "Predict car price (GET)", description = "Query params: brand, model, year, mileage")
    public ResponseEntity<ApiResponse<PricePredictionResponse>> predictPriceGet(
            @RequestParam String brand,
            @RequestParam String model,
            @RequestParam Integer year,
            @RequestParam(required = false) Integer mileage) {
        PricePredictionRequest req = new PricePredictionRequest();
        req.setBrand(brand);
        req.setModel(model);
        req.setYear(year);
        req.setMileage(mileage);
        return ResponseEntity.ok(ApiResponse.ok(predictionService.predictPrice(req)));
    }
}
