package ru.itis.carsharing.services.impl;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.carsharing.dto.TokenDto;
import ru.itis.carsharing.dto.UserDto;
import ru.itis.carsharing.form.SignInForm;
import ru.itis.carsharing.form.SignUpForm;
import ru.itis.carsharing.models.*;
import ru.itis.carsharing.repositories.TokensRepository;
import ru.itis.carsharing.repositories.UserRepository;
import ru.itis.carsharing.services.MailService;
import ru.itis.carsharing.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private TokensRepository tokensRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TokenDto login(SignInForm loginForm) {
        Optional<User> userCandidate = userRepository.find(loginForm.getEmail());

        if (userCandidate.isPresent()) {
            User user = userCandidate.get();


            if (passwordEncoder.matches(loginForm.getPassword(), user.getHashPassword())) {
                Token token = Token.builder()
                        .user(user)
                        .value(RandomStringUtils.random(10, true, true))
                        .build();


                tokensRepository.save(token);
                return TokenDto.from(token);
            }
        }
        throw new IllegalArgumentException("User not found");
    }

    @Override
    public void signUp(SignUpForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());

        User user = User.builder()
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .hashPassword(hashPassword)
                .email(userForm.getEmail())
                .role(Role.USER)
                .state(State.NOT_CONFIRMED)
                .build();

        userRepository.save(user);


        executorService.submit(() -> {
            Map<String, String> model = new HashMap<>();

            model.put("name", user.getEmail());
            model.put("link", "http://localhost:8080/confirm/" + user.getConfirmString());


            Mail mail = Mail.builder()
                    .subject("Registration")
                    .to(userForm.getEmail())
                    .model(model)
                    .build();

            mailService.sendConfirmEmail(mail);
        });
    }

    @Override
    public List<UserDto> findAll() {
        return UserDto.from(userRepository.findAll());
    }

    @Override
    public UserDto findOne(Long userId) {
        return UserDto.from(userRepository.find(userId).orElseThrow(() -> new IllegalArgumentException("no user with id = " + userId)));
    }

    @Override
    public void confirm(String confirmCode) {
        userRepository.deleteConfirmString(confirmCode);
    }
}
