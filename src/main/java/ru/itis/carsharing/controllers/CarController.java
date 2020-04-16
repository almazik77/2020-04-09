package ru.itis.carsharing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.carsharing.dto.CarDto;
import ru.itis.carsharing.form.AddCarForm;
import ru.itis.carsharing.models.Car;
import ru.itis.carsharing.models.User;
import ru.itis.carsharing.security.details.UserDetailsImpl;
import ru.itis.carsharing.services.CarService;
import ru.itis.carsharing.services.FilesService;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private FilesService filesService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/cars/add")
    public String getCarrAddPage() {
        return "car-add";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/cars/add")
    public String addCar(Authentication authentication, @RequestParam("uploadingFiles") MultipartFile[] uploadingFiles, @ModelAttribute AddCarForm carForm) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        Car car = Car.builder()
                .model(carForm.getModel())
                .cost(carForm.getCost())
                .owner(user)
                .build();

        carService.save(car);

        for (MultipartFile file : uploadingFiles) {
            filesService.save(file, car);
        }

        return "redirect:/cars";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/cars")
    public String getCarsPage(Model model) {
        List<CarDto> carList = carService.findAll();
        model.addAttribute("carList", carList);
        return "car-list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/cars/{car-id}")
    public String getCarPage(@PathVariable("car-id") Long carId, Model model) {
        CarDto carDto = carService.find(carId);
        if (carDto.getOrderSet() != null)
            carDto.setOrderSet(carDto.getOrderSet().stream().filter(order -> order.getEndDate().isAfter(ChronoLocalDate.from((LocalDate.now())))).collect(Collectors.toSet()));
        model.addAttribute("car", carDto);
        return "car-profile";
    }


}
