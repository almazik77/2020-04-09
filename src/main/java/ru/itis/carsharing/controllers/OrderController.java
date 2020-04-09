package ru.itis.carsharing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.carsharing.dto.UserDto;
import ru.itis.carsharing.form.OrderForm;
import ru.itis.carsharing.models.Order;
import ru.itis.carsharing.models.User;
import ru.itis.carsharing.security.details.UserDetailsImpl;
import ru.itis.carsharing.services.CarService;
import ru.itis.carsharing.services.OrderService;
import ru.itis.carsharing.services.UserService;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private CarService carService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/cars/{car-id}/order")
    public String getOrderPage(@PathVariable(name = "car-id") Long carId, Model model) {
        model.addAttribute("car", carService.find(carId));
        return "order";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/cars/{car-id}/order")
    public String createOrder(@PathVariable("car-id") Long carId, @ModelAttribute OrderForm form, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        orderService.createOrder(form, user, carId);
        return "redirect:/profile";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{user-id}/orders")
    public String getOrdersPage(@PathVariable("user-id") Long userId, Model model) {
        List<Order> orderList = userService.findOne(userId).getOrderList();
        UserDto userDto = userService.findOne(userId);
        model.addAttribute("user", userDto);
        model.addAttribute("orders", orderList);
        return "order-list";
    }
}
