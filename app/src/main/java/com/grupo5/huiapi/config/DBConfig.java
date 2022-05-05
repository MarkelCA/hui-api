package com.grupo5.huiapi.config;

import com.grupo5.huiapi.modules.category.entity.Category;
import com.grupo5.huiapi.modules.category.repository.CategoryRepository;
import com.grupo5.huiapi.modules.event.entity.Event;
import com.grupo5.huiapi.modules.event.repository.EventRepository;
import com.grupo5.huiapi.modules.user.entity.User;
import com.grupo5.huiapi.modules.user.modules.role.entity.Role;
import com.grupo5.huiapi.modules.user.modules.role.repository.RoleRepository;
import com.grupo5.huiapi.modules.user.repository.UserRepository;
import com.grupo5.huiapi.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DBConfig {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            categoryRepository.saveAll( getInitialCategories() );
            roleRepository.saveAll( getInitialRoles() );
            userRepository.saveAll( getInitialUsers() );
            eventRepository.saveAll( getInitialEvents() );

        };
    }
    private List<Role> getInitialRoles() {
        Role user = new Role("user");
        Role admin = new Role("admin");
        return List.of(user,admin);
    }

    private List<Event> getInitialEvents() {
        Optional<Category> futbolCat = categoryRepository.findCategoryByName("Fútbol");
        User user1 = userRepository.findById(1L).get();
        User user2 = userRepository.findById(2L).get();

        Event futbol = new Event("Partido de fútbol",
                "Un partido de fútbol amistoso entre los del barrio",
                user1);
        futbol.setLatitude(43.110786);
        futbol.setLongitude(-2.905345);
        futbol.addCategory(futbolCat.get());

        Optional<Category> boxingat = categoryRepository.findCategoryByName("Boxeo");
        Event boxeo = new Event(
                "Sparring de boxeo",
                "Entrenamiento de boxeo para principiantes",
                user2
                );
        boxeo.addCategory(boxingat.get());
        return List.of(futbol, boxeo);
    }

    private List<User> getInitialUsers() {
        Category boxingat = categoryRepository.findCategoryByName("Boxeo").get();
        Category crossfitCat = categoryRepository.findCategoryByName("Crossfit").get();
        Set<Category> favs1 = new HashSet<>(List.of(boxingat, crossfitCat));
        Role userRole = roleRepository.findByName("user").get();
        Role adminRole = roleRepository.findByName("admin").get();

        return List.of(
            new User(
                "jon33",
                "anotherpassword123",
                "jon@email.com",
                "Jon Arroita",
                    favs1
            ),
            new User(
                "markelca",
                "password123",
                "cuestaarribas.markel@gmail.com",
                "Markel Cuesta",
                "Soy muy majo y me gustan los deportes",
                    favs1
            ),
            new User(
                    "user",
                    "user",
                    "cuestaarriba.markel@gmail.com",
                    "User Usher",
                    "Soy muy majo y me gustan los deportes",
                        favs1
            )

        );
    }

    private List<Category> getInitialCategories() {
        // Principales
        List<Category> principales = Category.createCategories("Deportes", "Música", "Baile", "Fotografía");
        List<Category> ret = new ArrayList<>(principales);
        // Deportes
        Category deportes = ListUtils.getCategoryByName(principales, "Deportes");
        List<Category> deportesSubcategories = deportes.addSubCategories(
                "Fútbol",
                "Baloncesto",
                "Artes marciales",
                "Tenis",
                "Street",
                "Running",
                "Gym",
                "Calistenia",
                "Crossfit",
                "Padel"
        );
        ret.addAll(deportesSubcategories);
            // Artes Marciales
            Category artesMarciales = ListUtils.getCategoryByName(deportesSubcategories, "Artes marciales");
            List<Category> artesMarcialesSubCategories = artesMarciales.addSubCategories(
                "Karate", "MMA", "Jujutsu", "Taekwondo", "Boxeo", "Muay thai"
            );
            ret.addAll(artesMarcialesSubCategories);
            // Street
            Category street = ListUtils.getCategoryByName(deportesSubcategories, "Street") ;
            List<Category> streetSubCategories = street.addSubCategories("Parkour", "Skate");
            ret.addAll(streetSubCategories);

        return ret;
    }



}
