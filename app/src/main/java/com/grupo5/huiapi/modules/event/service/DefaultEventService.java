package com.grupo5.huiapi.modules.event.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.huiapi.exceptions.EntityNotFoundException;
import com.grupo5.huiapi.exceptions.IncorrectPasswordException;
import com.grupo5.huiapi.exceptions.RequiredValuesMissingException;
import com.grupo5.huiapi.modules.EntityType;
import com.grupo5.huiapi.modules.category.entity.Category;
import com.grupo5.huiapi.modules.category.service.CategoryService;
import com.grupo5.huiapi.modules.event.entity.Event;
import com.grupo5.huiapi.modules.event.repository.EventRepository;
import com.grupo5.huiapi.modules.user.entity.User;
import com.grupo5.huiapi.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

@org.springframework.stereotype.Service
@Qualifier("DefaultEventService")
public class DefaultEventService implements EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;
    @Autowired
    public DefaultEventService(EventRepository eventRepository, UserService userService, CategoryService categoryService, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Event get(Long id) throws EntityNotFoundException {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if(optionalEvent.isEmpty())
            throw new EntityNotFoundException(EntityType.EVENT);
        return optionalEvent.get();
    }
    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }
    @Override
    public String insert(JsonNode eventNode) throws EntityNotFoundException {
        Set<Category> categories = getCategoriesFromNode(eventNode);
        String title = eventNode.get("title").asText();
        String description = eventNode.get("description").asText();
        User user = userService.get( eventNode.get("organizer").asLong() );
        Event event = new Event(title, description, categories, user);
        eventRepository.save(event);
        return "Event registered";
    }
    public Set<Category> getCategoriesFromNode(JsonNode categoriesNode) throws EntityNotFoundException {
        JsonNode categories = categoriesNode.get("categories");
        List<Integer> categoryIds = objectMapper.convertValue(categories, ArrayList.class);
        return getCategoriesFromIds(categoryIds);
    }
    public Set<Category> getCategoriesFromIds(List<Integer> categories) throws EntityNotFoundException {
        Set<Category> ret = new HashSet<>();
        for (Integer category : categories) {
            ret.add(categoryService.get( Long.valueOf(category) ));
        }
        return ret;
    }
    @Override
    public String update(Long id, String password, JsonNode event) throws IncorrectPasswordException, RequiredValuesMissingException, EntityNotFoundException {
        // Event id exists
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty())
            throw new EntityNotFoundException(EntityType.EVENT);

        // User id exists
        Long organizerId = event.get("organizer").asLong();
        User user = userService.get(organizerId);
        if( !user.getPassword().equals(password) )
            throw new IncorrectPasswordException();

        // Categories exist
        Set<Category> categories = getCategoriesFromNode(event);
        String title = event.get("title").asText();
        String description  = event.get("description").asText();

        // Non missing required values
        Event updatingEvent = new Event(title, description, categories, user);
        String nullFields = updatingEvent.checkNullFields();
        if(nullFields != null)
            throw new RequiredValuesMissingException(nullFields);

        updatingEvent.setId(id);
        eventRepository.save(updatingEvent);
        return "Event updated";
    }
    @Override
    public String delete(Long id, String password) throws EntityNotFoundException {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if(optionalEvent.isEmpty())
            throw new EntityNotFoundException(EntityType.EVENT);



        eventRepository.delete(optionalEvent.get());
        return "Event removed";
    }
    @Override
    public String enroll(String password, Long userId, Long eventId) throws IncorrectPasswordException, EntityNotFoundException {
        User user = userService.get(userId);

        if( !user.getPassword().equals(password) )
            throw new IncorrectPasswordException();

        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if(optionalEvent.isEmpty())
            throw new EntityNotFoundException(EntityType.EVENT);

        Event event = optionalEvent.get();
        userService.enrollToEvent(event, user);

        //eventRepository.save(event);

        return "Enrolled to event";
    }
}
