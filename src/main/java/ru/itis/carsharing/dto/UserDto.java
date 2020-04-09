package ru.itis.carsharing.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.carsharing.models.Car;
import ru.itis.carsharing.models.Order;
import ru.itis.carsharing.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder()
public class UserDto {
    private Long id;

    private String email;

    private String firstName;
    private String lastName;

    private List<Order> orderList;
    private List<Car> carList;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .carList(user.getCarList())
                .orderList(user.getOrderList())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream().map(UserDto::from).collect(Collectors.toList());
    }


}
