package ru.itis.carsharing.services;

import ru.itis.carsharing.dto.TokenDto;
import ru.itis.carsharing.dto.UserDto;
import ru.itis.carsharing.form.SignInForm;
import ru.itis.carsharing.form.SignUpForm;

import java.util.List;

public interface UserService {
    TokenDto login(SignInForm loginForm);

    void signUp(SignUpForm userForm);

    List<UserDto> findAll();

    UserDto findOne(Long userId);

    void confirm(String confirmCode);
}
