package com.carmarketpro.payment.controller;

import com.carmarketpro.payment.domain.PaymentStatus;
import com.carmarketpro.payment.dto.PaymentRequest;
import com.carmarketpro.payment.dto.PaymentResponse;
import com.carmarketpro.payment.service.PaymentService;
import com.carmarketpro.common.api.ApiResponse;
import com.carmarketpro.common.api.Meta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Paiement")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    @Operation(summary = "List payments", description = "Filters: referenceId, status")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) UUID referenceId,
            @RequestParam(required = false) PaymentStatus status) {
        Sort s = sort != null && !sort.isBlank() ? Sort.by(Sort.Direction.ASC, sort.split(",")[0].trim()) : Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, s);
        Page<PaymentResponse> result = paymentService.findAll(referenceId, status, pageable);
        Meta meta = Meta.builder().page(result.getNumber()).size(result.getSize()).total(result.getTotalElements()).build();
        return ResponseEntity.ok(ApiResponse.ok(result.getContent(), meta));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID")
    public ResponseEntity<ApiResponse<PaymentResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(paymentService.findById(id)));
    }

    @PostMapping
    @Operation(summary = "Create payment")
    public ResponseEntity<ApiResponse<PaymentResponse>> create(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(paymentService.create(request)));
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Complete payment")
    public ResponseEntity<ApiResponse<PaymentResponse>> complete(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(paymentService.complete(id)));
    }
}
