package com.carmarketpro.rental.mapper;

import com.carmarketpro.rental.domain.Reservation;
import com.carmarketpro.rental.dto.ReservationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReservationMapper {

    ReservationResponse toResponse(Reservation reservation);
}
