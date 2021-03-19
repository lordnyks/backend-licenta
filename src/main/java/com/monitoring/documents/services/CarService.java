package com.monitoring.documents.services;

import com.monitoring.documents.model.CarEntity;
import com.monitoring.documents.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<CarEntity> getAllCars() {
        return carRepository.findAll();
    }


    public Optional<CarEntity> getCarById(Long carId) {
        return carRepository.findById(carId);
    }
}
