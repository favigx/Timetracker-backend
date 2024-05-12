package com.timetracker.timetracker.services;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.timetracker.timetracker.models.Session;
import com.timetracker.timetracker.models.Task;
import com.timetracker.timetracker.models.User;

@Service
public class TaskService {

    private final MongoOperations mongoOperations;

    public TaskService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Task addTask(Task task, String loggedInUserId) {
        Query query = new Query(Criteria.where("id").is(loggedInUserId));
        User loggedInUser = mongoOperations.findOne(query, User.class);

        ObjectId taskId = ObjectId.get();
        task.setId(taskId.toString());
        task.setUser(loggedInUser);

        return mongoOperations.insert(task);
    }

    public List<Task> getTasks() {
        return mongoOperations.findAll(Task.class);
    }

    public List<Task> getTasksForUser(String id) {
        Query query = new Query(Criteria.where("user.id").is(id));
        return mongoOperations.find(query, Task.class);
    }

    public List<Task> getTasksByTime(String id) {
        Criteria criteria = Criteria.where("user.id").is(id);
        Query query = new Query(criteria).with(Sort.by(Sort.Direction.DESC, "totalTime"));
        return mongoOperations.find(query, Task.class);
    }

    public Task getTaskById(String id) {
        return mongoOperations.findById(id, Task.class);
    }

    public Task editTaks(String id, Task task) {
        task.setId(id);
        return mongoOperations.save(task);
    }

    public Task editTimeTask(String id, Task task) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = new Update().inc("totalTime", task.getTotalTime());

        mongoOperations.updateFirst(query, update, Task.class);
        return mongoOperations.findById(id, Task.class);
    }

    public Task addSession(String id, Session session) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = new Update().addToSet("sessions", session);

        mongoOperations.updateFirst(query, update, Task.class);
        return mongoOperations.findById(id, Task.class);
    }

    public void deleteTask(String id) {
        Query query = Query.query(Criteria.where("id").is(id));

        mongoOperations.remove(query, Task.class);
    }

    public Task getTaskByTaskName(String taskName) {
        return mongoOperations.findOne(Query.query(Criteria.where("taskName").is(taskName)), Task.class);
    }
}