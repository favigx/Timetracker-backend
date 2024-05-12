package com.timetracker.timetracker.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.timetracker.timetracker.models.Session;
import com.timetracker.timetracker.models.Task;
import com.timetracker.timetracker.services.TaskService;

@CrossOrigin(origins = "https://goldfish-app-5o3ju.ondigitalocean.app/")
@RestController
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getRoot() {
        return "{'Hello': 'Timetracker!'}";
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/tasksforuser/{id}")
    public List<Task> getTasksForUser(@PathVariable String id) {
        return taskService.getTasksForUser(id);
    }

    @GetMapping("/tasksbytime/{id}")
    public List<Task> getTasksByTime(@PathVariable String id) {
        return taskService.getTasksByTime(id);
    }

    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/task/{id}")
    public Task addTask(@RequestBody Task task, @PathVariable String id) {
        return taskService.addTask(task, id);
    }

    @PatchMapping("/task/{id}")
    public Task editTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.editTaks(id, task);
    }

    @PatchMapping("/tasktime/{id}")
    public Task editTimeTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.editTimeTask(id, task);
    }

    @PostMapping("tasksession/{id}")
    public Task addSession(@PathVariable String id, @RequestBody Session session) {
        return taskService.addSession(id, session);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok("Task with id " + id + " has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete task");
        }
    }
}