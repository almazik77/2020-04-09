package ru.itis.carsharing.services;

import ru.itis.carsharing.dto.CarDto;
import ru.itis.carsharing.models.Car;

import java.util.List;

public interface CarService {
    List<CarDto> findAll();

    void save(Car car);

    CarDto find(Long carId);
}
