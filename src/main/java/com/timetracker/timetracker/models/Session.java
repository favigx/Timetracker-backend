package com.timetracker.timetracker.models;

public class Session {

    public String startTime;
    public String stopTime;
    public String sessionDate;
    public double time;
    public String taskId;
    public String userId;

    Task task;

    public Session(String startTime, String stopTime, String sessionDate, double time, String taskId, String userId,
            Task task) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.sessionDate = sessionDate;
        this.time = time;
        this.taskId = taskId;
        this.userId = userId;
        this.task = task;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}