package com.carmarketpro.catalog.repository;

import com.carmarketpro.catalog.domain.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

    Page<Car> findByBrandIgnoreCase(String brand, Pageable pageable);

    Page<Car> findByYear(Integer year, Pageable pageable);

    @Query("SELECT c FROM Car c WHERE (:brand IS NULL OR LOWER(c.brand) = LOWER(:brand)) " +
           "AND (:year IS NULL OR c.year = :year) AND (:availableSale IS NULL OR c.availableSale = :availableSale)")
    Page<Car> findByFilters(@Param("brand") String brand, @Param("year") Integer year,
                            @Param("availableSale") Boolean availableSale, Pageable pageable);
}
