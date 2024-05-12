package com.timetracker.timetracker.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.timetracker.timetracker.models.Admin;
import com.timetracker.timetracker.models.User;
import com.timetracker.timetracker.services.AdminService;
import com.timetracker.timetracker.services.UserService;

@CrossOrigin(origins = "https://goldfish-app-5o3ju.ondigitalocean.app/")
@RestController
public class AdminController {

    private AdminService adminService;
    private UserService userService;

    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public List<User> getUsersForAdmin() {
        return adminService.getUsersForAdmin();
    }

    @GetMapping("/admin/totaltime")
    public Map<String, String> getUserTotalTime() {
        return adminService.calculateTotalTimeForAllUsers();
    }

    @GetMapping("/admins")
    public List<User> getAdmins() {
        return adminService.getAdmins();
    }

    @GetMapping("/admin/{username}")
    public User getAdminById(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> addAdmin(@RequestBody Admin admin) {
        try {
            Admin addedAdmin = adminService.addAdmin(admin);
            return ResponseEntity.ok(addedAdmin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Det uppstod ett fel vid försök att lägga till användaren.");
        }
    }
}