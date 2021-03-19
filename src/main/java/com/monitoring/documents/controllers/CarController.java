package com.monitoring.documents.controllers;

import com.monitoring.documents.model.CarEntity;
import com.monitoring.documents.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public List<CarEntity> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping(path = "{carId}")
    public Optional<CarEntity> getCarById(@PathVariable("carId") Long carId) {
        return carService.getCarById(carId);
    }

}
