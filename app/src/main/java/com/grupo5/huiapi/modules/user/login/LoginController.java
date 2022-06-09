package com.grupo5.huiapi.modules.user.login;

import com.fasterxml.jackson.databind.JsonNode;
import com.grupo5.huiapi.exceptions.EntityNotFoundException;
import com.grupo5.huiapi.exceptions.IncorrectPasswordException;
import com.grupo5.huiapi.modules.user.entity.User;
import com.grupo5.huiapi.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
    private final UserService userService;

    public LoginController(@Qualifier("DefaultUserService") UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User login(@RequestBody JsonNode jsonUser) {
        try {
            User user = userService.login(jsonUser);
            return user;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IncorrectPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
    }
}
