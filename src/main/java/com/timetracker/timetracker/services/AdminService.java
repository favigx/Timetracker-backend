package com.timetracker.timetracker.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.timetracker.timetracker.models.Admin;
import com.timetracker.timetracker.models.User;

@Service
public class AdminService {

    private final MongoOperations mongoOperations;
    private PasswordEncoder passwordEncoder;

    public AdminService(MongoOperations mongoOperations, PasswordEncoder passwordEncoder) {
        this.mongoOperations = mongoOperations;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsersForAdmin() {
        return mongoOperations.findAll(User.class);
    }

    public void saveLoginTime(User user) {
        Date loginTime = new Date();
        if (user.getLogin() == null) {
            user.setLogin(new ArrayList<>());
        }
        user.getLogin().add(loginTime);
        mongoOperations.save(user);
        System.out.println("Inloggningsdatumet " + loginTime + " har sparats för användaren med ID " + user.getId());
    }

    public void saveLogoutTime(User user) {
        Date logoutTime = new Date();
        if (user.getLogout() == null) {
            user.setLogout(new ArrayList<>());
        }
        user.getLogout().add(logoutTime);
        mongoOperations.save(user);
        System.out.println("Utloggningsdatumet " + logoutTime + " har sparats för användaren med ID " + user.getId());
    }

    public Map<String, String> calculateTotalTimeForAllUsers() {
        List<User> users = mongoOperations.findAll(User.class);
        Map<String, String> totalTimeMap = new HashMap<>();

        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                List<Date> loginTimes = user.getLogin();
                List<Date> logoutTimes = user.getLogout();

                if (loginTimes.size() != logoutTimes.size()) {
                    throw new IllegalArgumentException(
                            "Antalet inloggningstider måste vara lika med antalet utloggningstider för användaren med ID: "
                                    + user.getId());
                }

                long totalActiveTimeMillis = 0;
                for (int i = 0; i < loginTimes.size(); i++) {
                    Date loginTime = loginTimes.get(i);
                    Date logoutTime = logoutTimes.get(i);

                    long activeTimeMillis = logoutTime.getTime() - loginTime.getTime();

                    totalActiveTimeMillis += activeTimeMillis;
                }

                long totalMinutes = TimeUnit.MILLISECONDS.toMinutes(totalActiveTimeMillis);
                long remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(totalActiveTimeMillis) % 60;

                String totalTimeString = totalMinutes + " minuter och " + remainingSeconds + " sekunder";
                String userInfo = user.getUsername() + " (ID: " + user.getId() + ")";
                totalTimeMap.put(userInfo, totalTimeString);
            }
        } else {
            totalTimeMap.put("Inga användare", "Inga användare hittades");
        }

        return totalTimeMap;
    }

    public List<User> getAdmins() {
        return mongoOperations.findAll(User.class);
    }

    public Admin getAdminByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoOperations.findOne(query, Admin.class);
    }

    public Admin getAdminById(String id) {
        return mongoOperations.findById(id, Admin.class);
    }

    public Admin addAdmin(Admin admin) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(admin.getUsername()));

        if (mongoOperations.exists(query, User.class)) {
            throw new RuntimeException("Användarnamnet finns redan");
        }

        String encryptedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encryptedPassword);
        return mongoOperations.insert(admin);
    }
}