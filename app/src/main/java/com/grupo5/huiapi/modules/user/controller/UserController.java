package com.grupo5.huiapi.modules.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.grupo5.huiapi.exceptions.*;
import com.grupo5.huiapi.modules.event.entity.Event;
import com.grupo5.huiapi.modules.user.entity.User;
import com.grupo5.huiapi.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("DefaultUserService") UserService userService) { this.userService = userService; }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping(path = "{id}")
    public User getUser(@PathVariable("id") Long id) {
        try {
            return userService.get(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String registerNewUser(@RequestBody JsonNode jsonUser) {
        try {
            return userService.insert(jsonUser);
        } catch (EmailTakenException | UsernameTakenException e) {
             throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (RequiredValuesMissingException | EntityNotFoundException | JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping(path = "{id}")
    public String deleteUser(@PathVariable("id") Long id, @RequestBody ObjectNode body) {
        String password = body.get("password").asText();
        try {
            return userService.delete(id, password);
        } catch (IncorrectPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping(path = "{id}")
    public String updateUser(@PathVariable("id") Long id, @RequestBody JsonNode body) {
        //String password = body.get("password").asText();
        try {
            return userService.update(id, "aa", body);
        } catch (IncorrectPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        } catch(EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch(JsonProcessingException | RequiredValuesMissingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);

        }
    }
    
    @GetMapping(path = "{id}/enrolled_events")
    public List<Event> getEnrolledEvents(@PathVariable("id") Long id) {
        try {
            return userService.getEnrolledEvents(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

}
