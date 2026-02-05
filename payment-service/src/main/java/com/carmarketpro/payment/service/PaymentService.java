package com.carmarketpro.payment.service;

import com.carmarketpro.payment.domain.Payment;
import com.carmarketpro.payment.domain.PaymentStatus;
import com.carmarketpro.payment.domain.ReferenceType;
import com.carmarketpro.payment.dto.PaymentRequest;
import com.carmarketpro.payment.dto.PaymentResponse;
import com.carmarketpro.payment.exception.ResourceNotFoundException;
import com.carmarketpro.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Page<PaymentResponse> findAll(UUID referenceId, PaymentStatus status, Pageable pageable) {
        if (referenceId != null) {
            return paymentRepository.findByReferenceId(referenceId, pageable).map(this::toResponse);
        }
        if (status != null) {
            return paymentRepository.findByStatus(status, pageable).map(this::toResponse);
        }
        return paymentRepository.findAll(pageable).map(this::toResponse);
    }

    public PaymentResponse findById(UUID id) {
        Payment p = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
        return toResponse(p);
    }

    @Transactional
    public PaymentResponse create(PaymentRequest request) {
        Payment p = Payment.builder()
                .referenceId(request.getReferenceId())
                .referenceType(request.getReferenceType())
                .amount(request.getAmount())
                .currency(request.getCurrency() != null ? request.getCurrency() : "EUR")
                .status(PaymentStatus.PENDING)
                .build();
        return toResponse(paymentRepository.save(p));
    }

    @Transactional
    public PaymentResponse complete(UUID id) {
        Payment p = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
        p.setStatus(PaymentStatus.SUCCESS);
        return toResponse(paymentRepository.save(p));
    }

    private PaymentResponse toResponse(Payment p) {
        return PaymentResponse.builder()
                .id(p.getId())
                .referenceId(p.getReferenceId())
                .referenceType(p.getReferenceType())
                .amount(p.getAmount())
                .currency(p.getCurrency())
                .status(p.getStatus())
                .createdAt(p.getCreatedAt())
                .build();
    }
}
