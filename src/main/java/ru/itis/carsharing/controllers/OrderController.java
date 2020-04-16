package ru.itis.carsharing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
public class OrderController {
    @Autowired
    private CarService carService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/cars/{car-id}/order")
    public String createOrder(@PathVariable("car-id") Long carId, @ModelAttribute OrderForm form, Authentication authentication, HttpServletRequest request) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        orderService.createOrder(form, user, carId);
        return "redirect:/profile";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{user-id}/orders")
    public String getOrdersPage(@PathVariable("user-id") Long userId, Model model) {
        Set<Order> orderSet = userService.findOne(userId).getOrderSet();
        UserDto userDto = userService.findOne(userId);
        model.addAttribute("user", userDto);
        model.addAttribute("orders", orderSet);
        return "order-list";
    }
}
