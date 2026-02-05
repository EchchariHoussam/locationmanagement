package com.carmarketpro.rental.repository;

import com.carmarketpro.rental.domain.Reservation;
import com.carmarketpro.rental.domain.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    Page<Reservation> findByUserId(UUID userId, Pageable pageable);

    Page<Reservation> findByStatus(ReservationStatus status, Pageable pageable);
}
