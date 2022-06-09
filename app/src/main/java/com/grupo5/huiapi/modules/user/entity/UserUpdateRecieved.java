package com.grupo5.huiapi.modules.user.entity;

public class UserUpdateRecieved {
    private String password;
    private UserParsetCategories user;

    public UserUpdateRecieved(String password, UserParsetCategories user) {
        this.password = password;
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserParsetCategories getUser() {
        return user;
    }

    public void setUser(UserParsetCategories user) {
        this.user = user;
    }
}
