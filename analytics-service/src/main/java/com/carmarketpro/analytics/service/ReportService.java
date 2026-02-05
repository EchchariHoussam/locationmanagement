package com.carmarketpro.analytics.service;

import com.carmarketpro.analytics.domain.Report;
import com.carmarketpro.analytics.dto.ReportResponse;
import com.carmarketpro.analytics.exception.ResourceNotFoundException;
import com.carmarketpro.analytics.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public Page<ReportResponse> findAll(String reportType, Pageable pageable) {
        if (reportType != null && !reportType.isBlank()) {
            return reportRepository.findByReportType(reportType, pageable).map(this::toResponse);
        }
        return reportRepository.findAll(pageable).map(this::toResponse);
    }

    public ReportResponse findById(UUID id) {
        Report r = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report", id));
        return toResponse(r);
    }

    private ReportResponse toResponse(Report r) {
        return ReportResponse.builder()
                .id(r.getId())
                .reportType(r.getReportType())
                .periodFrom(r.getPeriodFrom())
                .periodTo(r.getPeriodTo())
                .payload(r.getPayload())
                .createdAt(r.getCreatedAt())
                .build();
    }
}
