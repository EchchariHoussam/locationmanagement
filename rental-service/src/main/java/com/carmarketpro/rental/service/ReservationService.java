package com.carmarketpro.rental.service;

import com.carmarketpro.rental.domain.Reservation;
import com.carmarketpro.rental.domain.ReservationStatus;
import com.carmarketpro.rental.dto.ReservationRequest;
import com.carmarketpro.rental.dto.ReservationResponse;
import com.carmarketpro.rental.exception.ResourceNotFoundException;
import com.carmarketpro.rental.mapper.ReservationMapper;
import com.carmarketpro.rental.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public Page<ReservationResponse> findAll(UUID userId, ReservationStatus status, Pageable pageable) {
        if (userId != null) {
            return reservationRepository.findByUserId(userId, pageable).map(reservationMapper::toResponse);
        }
        if (status != null) {
            return reservationRepository.findByStatus(status, pageable).map(reservationMapper::toResponse);
        }
        return reservationRepository.findAll(pageable).map(reservationMapper::toResponse);
    }

    public ReservationResponse findById(UUID id) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        return reservationMapper.toResponse(r);
    }

    @Transactional
    public ReservationResponse create(ReservationRequest request) {
        Reservation r = Reservation.builder()
                .userId(request.getUserId())
                .carId(request.getCarId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(ReservationStatus.PENDING)
                .build();
        return reservationMapper.toResponse(reservationRepository.save(r));
    }

    @Transactional
    public ReservationResponse updateStatus(UUID id, ReservationStatus status) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        r.setStatus(status);
        return reservationMapper.toResponse(reservationRepository.save(r));
    }
}
