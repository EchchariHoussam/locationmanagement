package com.carmarketpro.catalog.mapper;

import com.carmarketpro.catalog.domain.Car;
import com.carmarketpro.catalog.dto.CarRequest;
import com.carmarketpro.catalog.dto.CarResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-05T01:30:43+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class CarMapperImpl implements CarMapper {

    @Override
    public CarResponse toResponse(Car car) {
        if ( car == null ) {
            return null;
        }

        CarResponse.CarResponseBuilder carResponse = CarResponse.builder();

        carResponse.id( car.getId() );
        carResponse.brand( car.getBrand() );
        carResponse.model( car.getModel() );
        carResponse.year( car.getYear() );
        carResponse.priceSale( car.getPriceSale() );
        carResponse.priceRentDay( car.getPriceRentDay() );
        carResponse.availableSale( car.isAvailableSale() );
        carResponse.availableRent( car.isAvailableRent() );
        carResponse.createdAt( car.getCreatedAt() );

        return carResponse.build();
    }

    @Override
    public Car toEntity(CarRequest request) {
        if ( request == null ) {
            return null;
        }

        Car.CarBuilder car = Car.builder();

        car.brand( request.getBrand() );
        car.model( request.getModel() );
        car.year( request.getYear() );
        car.priceSale( request.getPriceSale() );
        car.priceRentDay( request.getPriceRentDay() );
        if ( request.getAvailableSale() != null ) {
            car.availableSale( request.getAvailableSale() );
        }
        if ( request.getAvailableRent() != null ) {
            car.availableRent( request.getAvailableRent() );
        }

        return car.build();
    }
}
