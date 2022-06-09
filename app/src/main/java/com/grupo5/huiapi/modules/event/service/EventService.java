package com.grupo5.huiapi.modules.event.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.grupo5.huiapi.exceptions.CustomException;
import com.grupo5.huiapi.exceptions.EntityNotFoundException;
import com.grupo5.huiapi.exceptions.IncorrectPasswordException;
import com.grupo5.huiapi.exceptions.RequiredValuesMissingException;
import com.grupo5.huiapi.modules.Service;
import com.grupo5.huiapi.modules.event.entity.Event;

public interface EventService extends Service<Event, Long> {
    @Override
    Event get(Long id) throws EntityNotFoundException;
    @Override
    String insert(JsonNode eventNode) throws EntityNotFoundException;
    @Override
    String delete(Long id, String password) throws EntityNotFoundException;
    @Override
    String update(Long id, String password, JsonNode event) throws IncorrectPasswordException, RequiredValuesMissingException, EntityNotFoundException;

    String enroll(String password, Long userId, Long eventId) throws IncorrectPasswordException, EntityNotFoundException;
    
    List<Event> getCreatedEvents(Long user_id) throws EntityNotFoundException;
}
