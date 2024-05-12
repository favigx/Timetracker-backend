package com.timetracker.timetracker.services;

import java.util.Collections;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.timetracker.timetracker.models.Session;
import com.timetracker.timetracker.models.Task;

@Service
public class SessionService {

    private final MongoOperations mongoOperations;

    public SessionService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public List<Session> getSessions(String taskId) {
        Query query = new Query(Criteria.where("id").is(taskId));
        Task task = mongoOperations.findOne(query, Task.class);
        if (task != null) {
            return task.getSessions();
        } else {
            return Collections.emptyList();
        }
    }
}