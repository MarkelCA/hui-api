package com.grupo5.huiapi.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grupo5.huiapi.modules.category.entity.Category;
import com.grupo5.huiapi.modules.event.entity.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties({"categories"})
public class UserParsetCategories {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String description;
    private List<String> categories = new ArrayList<>();

    public UserParsetCategories(String username, String password, String email, String fullName, String description, List<String> categories) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
