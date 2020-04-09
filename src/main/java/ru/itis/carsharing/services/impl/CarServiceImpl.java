package ru.itis.carsharing.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.carsharing.dto.CarDto;
import ru.itis.carsharing.models.Car;
import ru.itis.carsharing.repositories.CarRepository;
import ru.itis.carsharing.services.CarService;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Override
    public List<CarDto> findAll() {
        return CarDto.from(carRepository.findAll());
    }

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    public CarDto find(Long carId) {
        Optional<Car> car = carRepository.find(carId);
        return CarDto.from(car.get());
    }
}
