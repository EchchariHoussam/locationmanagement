package com.carmarketpro.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CorrelationIdGatewayFilter implements GatewayFilter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Vérifier si le header existe déjà
        String correlationId = exchange.getRequest().getHeaders()
                .getFirst(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId.isEmpty()) {
            // Générer un nouvel ID unique si absent
            correlationId = UUID.randomUUID().toString();
        }

        // Ajouter le header dans la réponse
        exchange.getResponse().getHeaders().add(CORRELATION_ID_HEADER, correlationId);

        // Continuer le chain
        return chain.filter(exchange);
    }
}
