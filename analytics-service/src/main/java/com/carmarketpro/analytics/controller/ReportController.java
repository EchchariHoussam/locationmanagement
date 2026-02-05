package com.carmarketpro.analytics.controller;

import com.carmarketpro.analytics.dto.ReportResponse;
import com.carmarketpro.analytics.service.ReportService;
import com.carmarketpro.common.api.ApiResponse;
import com.carmarketpro.common.api.Meta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Reporting")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reports")
    @Operation(summary = "List reports", description = "Filter: reportType")
    public ResponseEntity<ApiResponse<List<ReportResponse>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String reportType) {
        Sort s = sort != null && !sort.isBlank() ? Sort.by(Sort.Direction.ASC, sort.split(",")[0].trim()) : Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, s);
        Page<ReportResponse> result = reportService.findAll(reportType, pageable);
        Meta meta = Meta.builder().page(result.getNumber()).size(result.getSize()).total(result.getTotalElements()).build();
        return ResponseEntity.ok(ApiResponse.ok(result.getContent(), meta));
    }

    @GetMapping("/reports/{id}")
    @Operation(summary = "Get report by ID")
    public ResponseEntity<ApiResponse<ReportResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(reportService.findById(id)));
    }
}
