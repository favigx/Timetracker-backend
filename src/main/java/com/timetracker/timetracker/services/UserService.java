package com.timetracker.timetracker.services;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.timetracker.timetracker.models.User;

@Service
public class UserService {

    private final MongoOperations mongoOperations;
    private PasswordEncoder passwordEncoder;

    public UserService(MongoOperations mongoOperations, PasswordEncoder passwordEncoder) {
        this.mongoOperations = mongoOperations;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return mongoOperations.findAll(User.class);
    }

    public User getUserByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoOperations.findOne(query, User.class);
    }

    public User getUserById(String id) {
        return mongoOperations.findById(id, User.class);
    }

    public User addUser(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(user.getUsername()));

        if (mongoOperations.exists(query, User.class)) {
            throw new RuntimeException("Användarnamnet finns redan");
        }

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return mongoOperations.insert(user);
    }

    public void deleteUser(String userId) {
        Query query = Query.query(Criteria.where("id").is(userId));
        mongoOperations.remove(query, User.class);
    }
}