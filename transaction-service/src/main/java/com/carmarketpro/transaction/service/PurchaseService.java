package com.carmarketpro.transaction.service;

import com.carmarketpro.transaction.domain.Purchase;
import com.carmarketpro.transaction.domain.PurchaseStatus;
import com.carmarketpro.transaction.dto.PurchaseRequest;
import com.carmarketpro.transaction.dto.PurchaseResponse;
import com.carmarketpro.transaction.exception.ResourceNotFoundException;
import com.carmarketpro.transaction.mapper.PurchaseMapper;
import com.carmarketpro.transaction.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;

    public Page<PurchaseResponse> findAll(UUID userId, PurchaseStatus status, Pageable pageable) {
        if (userId != null) {
            return purchaseRepository.findByUserId(userId, pageable).map(purchaseMapper::toResponse);
        }
        if (status != null) {
            return purchaseRepository.findByStatus(status, pageable).map(purchaseMapper::toResponse);
        }
        return purchaseRepository.findAll(pageable).map(purchaseMapper::toResponse);
    }

    public PurchaseResponse findById(UUID id) {
        Purchase p = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", id));
        return purchaseMapper.toResponse(p);
    }

    @Transactional
    public PurchaseResponse create(PurchaseRequest request) {
        Purchase p = Purchase.builder()
                .userId(request.getUserId())
                .carId(request.getCarId())
                .amount(request.getAmount())
                .status(PurchaseStatus.PENDING)
                .build();
        return purchaseMapper.toResponse(purchaseRepository.save(p));
    }

    @Transactional
    public PurchaseResponse linkPayment(UUID id, UUID paymentId) {
        Purchase p = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", id));
        p.setPaymentId(paymentId);
        p.setStatus(PurchaseStatus.PAID);
        return purchaseMapper.toResponse(purchaseRepository.save(p));
    }
}
