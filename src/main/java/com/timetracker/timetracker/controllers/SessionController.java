package com.timetracker.timetracker.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.timetracker.timetracker.models.Session;
import com.timetracker.timetracker.services.SessionService;

@CrossOrigin(origins = "https://goldfish-app-5o3ju.ondigitalocean.app/")
@RestController
public class SessionController {

    private SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/session/{taskId}")
    public List<Session> getSessions(@PathVariable String taskId) {
        return sessionService.getSessions(taskId);
    }
}