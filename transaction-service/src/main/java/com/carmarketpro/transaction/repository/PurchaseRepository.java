package com.carmarketpro.transaction.repository;

import com.carmarketpro.transaction.domain.Purchase;
import com.carmarketpro.transaction.domain.PurchaseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {

    Page<Purchase> findByUserId(UUID userId, Pageable pageable);

    Page<Purchase> findByStatus(PurchaseStatus status, Pageable pageable);
}
