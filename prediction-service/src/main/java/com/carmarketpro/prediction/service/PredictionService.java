package com.carmarketpro.prediction.service;

import com.carmarketpro.prediction.dto.PricePredictionRequest;
import com.carmarketpro.prediction.dto.PricePredictionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service de prédiction de prix. En production, appeler un service ML externe via HTTP.
 * Ici: règle heuristique simple pour démo (remplacer par appel REST vers modèle ML).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PredictionService {

    @Value("${app.prediction.ml-url:}")
    private String mlServiceUrl;

    public PricePredictionResponse predictPrice(PricePredictionRequest request) {
        if (mlServiceUrl != null && !mlServiceUrl.isBlank()) {
            return callMlService(request);
        }
        // Fallback: heuristique simple (année + base)
        BigDecimal base = new BigDecimal("10000");
        int yearFactor = Math.max(0, request.getYear() - 2000);
        BigDecimal yearBonus = BigDecimal.valueOf(yearFactor * 500);
        BigDecimal mileageFactor = request.getMileage() != null
                ? BigDecimal.valueOf(Math.max(0, 150000 - request.getMileage())).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
        BigDecimal predicted = base.add(yearBonus).add(mileageFactor).setScale(2, RoundingMode.HALF_UP);
        log.info("Predicted price for {} {} ({}): {} EUR", request.getBrand(), request.getModel(), request.getYear(), predicted);
        return PricePredictionResponse.builder()
                .predictedPrice(predicted)
                .currency("EUR")
                .modelVersion("heuristic-v1")
                .build();
    }

    private PricePredictionResponse callMlService(PricePredictionRequest request) {
        // TODO: WebClient vers app.prediction.ml-url avec request, parser réponse
        // Pour l'instant même heuristique
        return predictPrice(request);
    }
}
