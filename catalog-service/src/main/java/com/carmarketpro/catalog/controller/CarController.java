package com.carmarketpro.catalog.controller;

import com.carmarketpro.catalog.dto.CarRequest;
import com.carmarketpro.catalog.dto.CarResponse;
import com.carmarketpro.catalog.service.CarService;
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
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
@Tag(name = "Catalog", description = "Car catalog - list, create, update, delete")
public class CarController {

    private final CarService carService;

    // Paginated list
    @GetMapping("/cars")
    @Operation(summary = "List cars", description = "Paginated with filters: brand, year, availableSale")
    public ResponseEntity<ApiResponse<List<CarResponse>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Boolean availableSale) {

        Sort s = sort != null && !sort.isBlank()
                ? Sort.by(Sort.Direction.ASC, sort.split(",")[0].trim())
                : Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(page, size, s);
        Page<CarResponse> result = carService.findAll(brand, year, availableSale, pageable);

        Meta meta = Meta.builder()
                .page(result.getNumber())
                .size(result.getSize())
                .total(result.getTotalElements())
                .build();

        return ResponseEntity.ok(ApiResponse.ok(result.getContent(), meta));
    }

    // Get car by ID
    @GetMapping("/cars/{id}")
    @Operation(summary = "Get car by ID")
    public ResponseEntity<ApiResponse<CarResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(carService.findById(id)));
    }

    // Create car
    @PostMapping("/cars")
    @Operation(summary = "Create car")
    public ResponseEntity<ApiResponse<CarResponse>> create(@Valid @RequestBody CarRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(carService.create(request)));
    }

    // Update car
    @PutMapping("/cars/{id}")
    @Operation(summary = "Update car")
    public ResponseEntity<ApiResponse<CarResponse>> update(@PathVariable UUID id, @Valid @RequestBody CarRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(carService.update(id, request)));
    }

    // Delete car
    @DeleteMapping("/cars/{id}")
    @Operation(summary = "Delete car")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        carService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Deleted"));
    }

    // New endpoint: all cars
    @GetMapping("/all-cars")
    @Operation(summary = "Get all cars", description = "Return all cars without pagination")
    public ResponseEntity<ApiResponse<List<CarResponse>>> allCars() {
        List<CarResponse> cars = carService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(cars));
    }
}
