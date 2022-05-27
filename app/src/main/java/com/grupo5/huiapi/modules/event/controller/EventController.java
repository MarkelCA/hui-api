package com.grupo5.huiapi.modules.event.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.grupo5.huiapi.modules.EntityType;
import com.grupo5.huiapi.exceptions.*;
import com.grupo5.huiapi.modules.event.service.EventService;
import com.grupo5.huiapi.modules.event.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(@Qualifier("DefaultEventService") EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getEvents() { return eventService.getAll();}

    @GetMapping(path = "{id}")
    public Event getEvent(@PathVariable Long id) {
        try {
            return eventService.get(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewEvent(@RequestBody JsonNode jsonEvent) {
        try {
            return eventService.insert(jsonEvent);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping(path = "{id}")
    public String updateEvent(@PathVariable("id") Long id, @RequestBody JsonNode body) throws JsonProcessingException {
        String password = "uaoeu";
        try {
            return eventService.update(id, password, body);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IncorrectPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        } catch (RequiredValuesMissingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping(path = "{id}")
    public String deleteEvent(@PathVariable("id") Long id, @RequestBody JsonNode body) {
        String password = body.get("password").asText();
        try {
            return eventService.delete(id, password);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping(path = "{id}/enroll")
    public String enrollToEvent(@PathVariable("id") Long eventId, @RequestBody JsonNode body) {
        String password = body.get("password").asText();
        Long userId = body.get("userId").asLong();
        try {
            return eventService.enroll(password, userId, eventId);
        } catch (IncorrectPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
    
    @GetMapping(path = "{id}/created_events")
    public List<Event> getCreatedEvents(@PathVariable("id") Long id) {
        try {
            return eventService.getCreatedEvents(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

}
