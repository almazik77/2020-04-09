package ru.itis.carsharing.dto;

import lombok.*;
import ru.itis.carsharing.models.Car;
import ru.itis.carsharing.models.FileInfo;
import ru.itis.carsharing.models.Order;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    private Long id;

    private String model;

    private Long cost;

    private UserDto owner;

    private List<FileInfo> fileList;

    private List<Order> orderList;

    public static CarDto from(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .model(car.getModel())
                .cost(car.getCost())
                .owner(UserDto.from(car.getOwner()))
                .fileList(car.getFileList())
                .build();
    }

    public static List<CarDto> from(List<Car> cars) {
        return cars.stream().map(CarDto::from).collect(Collectors.toList());
    }


}
