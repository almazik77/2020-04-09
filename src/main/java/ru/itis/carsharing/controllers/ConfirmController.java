package ru.itis.carsharing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.carsharing.services.UserService;

@Controller
public class ConfirmController {

    @Autowired
    private UserService signUpService;

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/confirm/{confirm-code:.+}", method = RequestMethod.GET)
    public ModelAndView confirmEmail(@PathVariable("confirm-code") String confirmCode) {
        signUpService.confirm(confirmCode);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success_confirm_page");
        return modelAndView;
    }
}
