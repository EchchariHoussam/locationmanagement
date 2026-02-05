package com.carmarketpro.rental.controller;

import com.carmarketpro.rental.domain.ReservationStatus;
import com.carmarketpro.rental.dto.ReservationRequest;
import com.carmarketpro.rental.dto.ReservationResponse;
import com.carmarketpro.rental.service.ReservationService;
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
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Tag(name = "Rentals", description = "Reservation location")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    @Operation(summary = "List reservations", description = "Filters: userId, status")
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) ReservationStatus status) {
        Sort s = sort != null && !sort.isBlank() ? Sort.by(Sort.Direction.ASC, sort.split(",")[0].trim()) : Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, s);
        Page<ReservationResponse> result = reservationService.findAll(userId, status, pageable);
        Meta meta = Meta.builder().page(result.getNumber()).size(result.getSize()).total(result.getTotalElements()).build();
        return ResponseEntity.ok(ApiResponse.ok(result.getContent(), meta));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reservation by ID")
    public ResponseEntity<ApiResponse<ReservationResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(reservationService.findById(id)));
    }

    @PostMapping
    @Operation(summary = "Create reservation")
    public ResponseEntity<ApiResponse<ReservationResponse>> create(@Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(reservationService.create(request)));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update reservation status")
    public ResponseEntity<ApiResponse<ReservationResponse>> updateStatus(@PathVariable UUID id, @RequestParam ReservationStatus status) {
        return ResponseEntity.ok(ApiResponse.ok(reservationService.updateStatus(id, status)));
    }
}
