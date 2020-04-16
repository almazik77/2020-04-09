package ru.itis.carsharing.repositories;

import ru.itis.carsharing.models.Car;

import java.util.Set;

public interface CarRepository extends CrudRepository<Long, Car> {
    Set<Car> findByOwnerId(Long id);
}
