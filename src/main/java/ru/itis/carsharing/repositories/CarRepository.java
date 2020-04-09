package ru.itis.carsharing.repositories;

import ru.itis.carsharing.models.Car;

import java.util.List;

public interface CarRepository extends CrudRepository<Long, Car> {
    List<Car> findByOwnerId(Long id);
}
