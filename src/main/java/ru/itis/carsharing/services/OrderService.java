package ru.itis.carsharing.services;

import ru.itis.carsharing.form.OrderForm;
import ru.itis.carsharing.models.User;

public interface OrderService {
    void createOrder(OrderForm form, User user, Long carId);
}
