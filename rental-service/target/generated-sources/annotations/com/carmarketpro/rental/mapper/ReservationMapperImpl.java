package com.carmarketpro.rental.mapper;

import com.carmarketpro.rental.domain.Reservation;
import com.carmarketpro.rental.dto.ReservationResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-03T21:20:39+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    @Override
    public ReservationResponse toResponse(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationResponse.ReservationResponseBuilder reservationResponse = ReservationResponse.builder();

        reservationResponse.id( reservation.getId() );
        reservationResponse.userId( reservation.getUserId() );
        reservationResponse.carId( reservation.getCarId() );
        reservationResponse.startDate( reservation.getStartDate() );
        reservationResponse.endDate( reservation.getEndDate() );
        reservationResponse.totalAmount( reservation.getTotalAmount() );
        reservationResponse.status( reservation.getStatus() );
        reservationResponse.createdAt( reservation.getCreatedAt() );

        return reservationResponse.build();
    }
}
