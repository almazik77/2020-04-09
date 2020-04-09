package ru.itis.carsharing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.carsharing.form.SignUpForm;
import ru.itis.carsharing.services.UserService;

@Controller
public class SignUpController {
    @Autowired
    private UserService userService;

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "sign-up";
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    public String signUp(@ModelAttribute SignUpForm signUpForm) {
        userService.signUp(signUpForm);
        return "redirect:/signIn";
    }
}
