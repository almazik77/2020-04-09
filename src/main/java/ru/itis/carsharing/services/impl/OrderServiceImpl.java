package ru.itis.carsharing.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.carsharing.dto.CarDto;
import ru.itis.carsharing.form.OrderForm;
import ru.itis.carsharing.models.Car;
import ru.itis.carsharing.models.Order;
import ru.itis.carsharing.models.User;
import ru.itis.carsharing.repositories.OrderRepository;
import ru.itis.carsharing.services.CarService;
import ru.itis.carsharing.services.OrderService;
import ru.itis.carsharing.services.UserService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void createOrder(OrderForm form, User user, Long carId) {
        CarDto carDto = carService.find(carId);
//        UserDto ownerDto = carDto.getOwner();
//        User owner = User.builder()
//                .id(ownerDto.getId())
//                .build();
        Car car = Car.builder()
                .id(carDto.getId())
                .build();

        Order order = Order.builder()
                .beginDate(form.getBeginDate())
                .endDate(form.getEndDate())
                .car(car)
                .user(user)
                .isPayed(Boolean.FALSE)
                .build();


        orderRepository.save(order);

    }
}
