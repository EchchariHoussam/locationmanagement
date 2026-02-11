package com.carmarketpro.catalog.service;

import com.carmarketpro.catalog.domain.Car;
import com.carmarketpro.catalog.dto.CarRequest;
import com.carmarketpro.catalog.dto.CarResponse;
import com.carmarketpro.catalog.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    /* ================= CREATE ================= */
    public CarResponse create(CarRequest request) {
        Car car = new Car();
        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setPriceSale(request.getPriceSale());
        car.setPriceRentDay(request.getPriceRentDay());
        car.setAvailableSale(request.getAvailableSale() != null ? request.getAvailableSale() : true);
        car.setAvailableRent(request.getAvailableRent() != null ? request.getAvailableRent() : true);

        return mapToResponse(carRepository.save(car));
    }

    /* ================= UPDATE ================= */
    public CarResponse update(UUID id, CarRequest request) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setPriceSale(request.getPriceSale());
        car.setPriceRentDay(request.getPriceRentDay());
        if (request.getAvailableSale() != null) car.setAvailableSale(request.getAvailableSale());
        if (request.getAvailableRent() != null) car.setAvailableRent(request.getAvailableRent());

        return mapToResponse(carRepository.save(car));
    }

    /* ================= DELETE ================= */
    public void delete(UUID id) {
        if (!carRepository.existsById(id)) {
            throw new EntityNotFoundException("Car not found");
        }
        carRepository.deleteById(id);
    }

    /* ================= READ ================= */
    public CarResponse findById(UUID id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        return mapToResponse(car);
    }

    public Page<CarResponse> findAll(String brand, Integer year, Boolean availableSale, Pageable pageable) {
        Page<Car> carsPage = carRepository.findAll(pageable);

        List<CarResponse> filtered = carsPage.stream()
                .filter(car -> {
                    if (brand != null && !car.getBrand().equalsIgnoreCase(brand)) return false;
                    if (year != null && !car.getYear().equals(year)) return false;
                    if (availableSale != null && car.isAvailableSale() != availableSale) return false;
                    return true;
                })
                .map(this::mapToResponse)
                .toList();

        return new org.springframework.data.domain.PageImpl<>(filtered, pageable, carsPage.getTotalElements());
    }

    // Surcharge pour retourner toutes les voitures sans pagination
    public List<CarResponse> findAll() {
        return carRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* ================= HELPER ================= */
    private CarResponse mapToResponse(Car car) {
        CarResponse response = new CarResponse();
        response.setId(car.getId());
        response.setBrand(car.getBrand());
        response.setModel(car.getModel());
        response.setYear(car.getYear());
        response.setPriceSale(car.getPriceSale());
        response.setPriceRentDay(car.getPriceRentDay());
        response.setAvailableSale(car.isAvailableSale());
        response.setAvailableRent(car.isAvailableRent());
        return response;
    }
}
