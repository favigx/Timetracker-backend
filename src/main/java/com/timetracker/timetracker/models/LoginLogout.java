package com.timetracker.timetracker.models;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DocumentReference;

public class LoginLogout {

    public Date login;
    public Date logout;
    public long activeTime;

    @DocumentReference
    User user;

    public LoginLogout(Date login, Date logout, long activeTime, User user) {
        this.login = login;
        this.logout = logout;
        this.activeTime = activeTime;
        this.user = user;
    }

    public Date getLogin() {
        return login;
    }

    public void setLogin(Date login) {
        this.login = login;
    }

    public Date getLogout() {
        return logout;
    }

    public void setLogout(Date logout) {
        this.logout = logout;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(long activeTime) {
        this.activeTime = activeTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}