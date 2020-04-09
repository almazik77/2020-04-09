package ru.itis.carsharing.repositories;

import ru.itis.carsharing.models.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Long, Order> {
    void save(Order order);

    List<Order> findByCarId(Long id);

    List<Order> findByUserId(long id);
}
