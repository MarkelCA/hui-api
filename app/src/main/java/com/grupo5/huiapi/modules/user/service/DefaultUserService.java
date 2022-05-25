package com.grupo5.huiapi.modules.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.grupo5.huiapi.modules.EntityType;
import com.grupo5.huiapi.exceptions.*;
import com.grupo5.huiapi.modules.category.entity.Category;
import com.grupo5.huiapi.modules.category.service.CategoryService;
import com.grupo5.huiapi.modules.event.entity.Event;
import com.grupo5.huiapi.modules.user.entity.User;
import com.grupo5.huiapi.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Qualifier("DefaultUserService")
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    @Autowired
    public DefaultUserService(UserRepository userRepository, CategoryService categoryService, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.objectMapper = objectMapper;
    }
    public User get(Long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new EntityNotFoundException(EntityType.USER);
        return user.get();
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }
    public String insert(JsonNode jsonUser) throws EmailTakenException, UsernameTakenException, RequiredValuesMissingException, EntityNotFoundException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.treeToValue(jsonUser, User.class);

        Optional<User> userOptionalByMail= userRepository.findUserByEmail(user.getEmail());
        if(userOptionalByMail.isPresent())
            throw new EmailTakenException();

        Optional<User> userOptionalByUsername = userRepository.findUserByUsername(user.getUsername());
        if(userOptionalByUsername.isPresent())
            throw new UsernameTakenException();

        String missingValues = user.checkNullFields();
        if(missingValues != null)
            throw new RequiredValuesMissingException(missingValues);
        System.out.println(user);

        userRepository.save(user);

        //log.info("User successfully registered");
        return "User successfully registered";
    }
    public String delete(Long id, String password) throws IncorrectPasswordException, EntityNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty())
            throw new EntityNotFoundException(EntityType.USER);

        User user = optionalUser.get();
        if(!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException();
        }

        userRepository.delete(user);
        return "User successfully deleted";
    }
    public Set<Category> getCategoriesFromNode(JsonNode categoriesNode) throws EntityNotFoundException {
        JsonNode categories = categoriesNode.get("categories");
        List<String> categoryIds = new ArrayList<>();
        try {
            categoryIds = objectMapper.readValue(categories.asText(), TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ;
        return getCategoriesFromName(categoryIds);
    }

    public Set<Category> getCategoriesFromName(List<String> categories) throws EntityNotFoundException {
        Set<Category> ret = new HashSet<>();
        for (String category : categories) {
            ret.add(categoryService.get(category));
        }
        return ret;
    }

    public Set<Category> getCategoriesFromIds(List<Integer> categories) throws EntityNotFoundException {
        Set<Category> ret = new HashSet<>();
        for (Integer category : categories) {
            ret.add(categoryService.get(Long.valueOf(category)));
        }
        return ret;
    }
    public String update(Long id, String password, JsonNode updatingJsonUser) throws IncorrectPasswordException, RequiredValuesMissingException, EntityNotFoundException, JsonProcessingException {
        User updatingUser = objectMapper.treeToValue(updatingJsonUser, User.class);

        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty())
            throw new EntityNotFoundException(EntityType.USER);

        User originalUser = optionalUser.get();

        if( !originalUser.getPassword().equals(password) ) {
            throw new IncorrectPasswordException();
        }

        // Comprobamos si tiene todos los campos obligatorios
        String nullFields = updatingUser.checkNullFields();
        if(nullFields != null)
            throw new RequiredValuesMissingException(nullFields);

        updatingUser.setId(id);
        userRepository.save(updatingUser);
        return "User updated";
    }
    public void enrollToEvent(Event event, User user) {
        user.getEnrolled_events().add(event);
        userRepository.save(user);
    }

    @Override
    public User login(JsonNode jsonUser) throws EntityNotFoundException, IncorrectPasswordException {
        if(jsonUser.isEmpty())
            throw new EntityNotFoundException(EntityType.USER);

        System.out.println(jsonUser);

        String username = jsonUser.get("username").asText();
        String inputPassword = jsonUser.get("password").asText();

        Optional<User> optionalUser = userRepository.findUserByUsername(username);

        if(optionalUser.isEmpty())
            throw new EntityNotFoundException(EntityType.USER);

        User user = optionalUser.get();

        if(!inputPassword.equals(user.getPassword()))
            throw new IncorrectPasswordException();

        return user;
    }
	@Override
	public List<Event> getEnrolledEvents(Long user_id) throws EntityNotFoundException {
		
		System.out.println(user_id);
		
		List<Event> enrolled_Events;

		 Optional<User> user = userRepository.findById(user_id);
	        if(user.isEmpty()) {
	        	 throw new EntityNotFoundException(EntityType.USER);
	        }else {
	        	enrolled_Events = new ArrayList(user.get().getEnrolled_events());
	        	 //userRepository.findEnrolledEvents(user_id);
	        }

        return enrolled_Events;

	}


}
