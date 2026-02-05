package com.carmarketpro.catalog.mapper;

import com.carmarketpro.catalog.domain.Car;
import com.carmarketpro.catalog.dto.CarRequest;
import com.carmarketpro.catalog.dto.CarResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    CarResponse toResponse(Car car);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Car toEntity(CarRequest request);
}
