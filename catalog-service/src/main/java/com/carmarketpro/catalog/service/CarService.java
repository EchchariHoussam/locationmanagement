package com.carmarketpro.catalog.service;

import com.carmarketpro.catalog.dto.CarRequest;
import com.carmarketpro.catalog.dto.CarResponse;
import com.carmarketpro.catalog.entity.Car;
import com.carmarketpro.catalog.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    // Create
    public CarResponse create(CarRequest request) {
        Car car = new Car();
        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setPriceSale(request.getPriceSale());
        car.setPriceRentDay(request.getPriceRentDay());
        car.setAvailableSale(request.getAvailableSale() != null ? request.getAvailableSale() : true);
        car.setAvailableRent(request.getAvailableRent() != null ? request.getAvailableRent() : true);
        car = carRepository.save(car);
        return mapToResponse(car);
    }

    // Update
    public CarResponse update(UUID id, CarRequest request) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setPriceSale(request.getPriceSale());
        car.setPriceRentDay(request.getPriceRentDay());
        car.setAvailableSale(request.getAvailableSale() != null ? request.getAvailableSale() : car.getAvailableSale());
        car.setAvailableRent(request.getAvailableRent() != null ? request.getAvailableRent() : car.getAvailableRent());
        car = carRepository.save(car);
        return mapToResponse(car);
    }

    // Delete
    public void delete(UUID id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
        carRepository.delete(car);
    }

    // Find by ID
    public CarResponse findById(UUID id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
        return mapToResponse(car);
    }

    // Paginated list
    public Page<CarResponse> findAll(String brand, Integer year, Boolean availableSale, Pageable pageable) {
        Page<Car> page = carRepository.findAll(pageable)
                .map(car -> {
                    if (brand != null && !car.getBrand().equalsIgnoreCase(brand)) return null;
                    if (year != null && !car.getYear().equals(year)) return null;
                    if (availableSale != null && !car.getAvailableSale().equals(availableSale)) return null;
                    return car;
                });
        return page.map(this::mapToResponse);
    }

    // All cars
    public List<CarResponse> findAll() {
        return carRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Manual mapping Car -> CarResponse
    private CarResponse mapToResponse(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .priceSale(car.getPriceSale())
                .priceRentDay(car.getPriceRentDay())
                .availableSale(car.getAvailableSale() != null ? car.getAvailableSale() : false)
                .availableRent(car.getAvailableRent() != null ? car.getAvailableRent() : false)
                .createdAt(car.getCreatedAt())
                .build();
    }
}
