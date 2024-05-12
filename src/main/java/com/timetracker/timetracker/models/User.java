package com.timetracker.timetracker.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {

    @Id
    public String id;

    @Indexed(unique = true)
    public String username;

    public String password;
    public String name;
    public String email;
    private List<Date> login;
    private List<Date> logout;

    public User(String id, String username, String password, String name, String email, List<Date> login,
            List<Date> logout) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.login = login;
        this.logout = logout;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Date> getLogin() {
        return login;
    }

    public void setLogin(List<Date> login) {
        this.login = login;
    }

    public List<Date> getLogout() {
        return logout;
    }

    public void setLogout(List<Date> logout) {
        this.logout = logout;
    }
}