package com.carmarketpro.transaction.controller;

import com.carmarketpro.transaction.domain.PurchaseStatus;
import com.carmarketpro.transaction.dto.PurchaseRequest;
import com.carmarketpro.transaction.dto.PurchaseResponse;
import com.carmarketpro.transaction.service.PurchaseService;
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
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Achat / Purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    @Operation(summary = "List purchases", description = "Filters: userId, status")
    public ResponseEntity<ApiResponse<List<PurchaseResponse>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) PurchaseStatus status) {
        Sort s = sort != null && !sort.isBlank() ? Sort.by(Sort.Direction.ASC, sort.split(",")[0].trim()) : Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, s);
        Page<PurchaseResponse> result = purchaseService.findAll(userId, status, pageable);
        Meta meta = Meta.builder().page(result.getNumber()).size(result.getSize()).total(result.getTotalElements()).build();
        return ResponseEntity.ok(ApiResponse.ok(result.getContent(), meta));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get purchase by ID")
    public ResponseEntity<ApiResponse<PurchaseResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(purchaseService.findById(id)));
    }

    @PostMapping
    @Operation(summary = "Create purchase")
    public ResponseEntity<ApiResponse<PurchaseResponse>> create(@Valid @RequestBody PurchaseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(purchaseService.create(request)));
    }

    @PatchMapping("/{id}/payment")
    @Operation(summary = "Link payment to purchase")
    public ResponseEntity<ApiResponse<PurchaseResponse>> linkPayment(@PathVariable UUID id, @RequestParam UUID paymentId) {
        return ResponseEntity.ok(ApiResponse.ok(purchaseService.linkPayment(id, paymentId)));
    }
}
