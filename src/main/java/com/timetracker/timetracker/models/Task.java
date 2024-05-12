package com.timetracker.timetracker.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "Tasks")
public class Task {
    @Id
    private String id;
    private String taskName;
    private String comment;
    private double totalTime;
    private List<Session> sessions;

    @DocumentReference
    User user;

    public Task(String id, String taskName, String comment, double totalTime, List<Session> sessions, User user) {
        this.id = id;
        this.taskName = taskName;
        this.comment = comment;
        this.totalTime = totalTime;
        this.sessions = sessions;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}