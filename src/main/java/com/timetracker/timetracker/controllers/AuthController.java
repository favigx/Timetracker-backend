package com.timetracker.timetracker.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.timetracker.timetracker.models.Admin;
import com.timetracker.timetracker.models.User;
import com.timetracker.timetracker.services.AdminService;
import com.timetracker.timetracker.services.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = "https://squid-app-ibi5o.ondigitalocean.app")
@RestController
public class AuthController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private AdminService adminService;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, AdminService adminService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
    }

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpriationMs;

    @PostMapping("/loginuser")
    public ResponseEntity<?> login(@RequestBody User user) {
        User existingUser = userService.getUserByUsername(user.getUsername());

        if (existingUser != null) {
            String encodedPassword = existingUser.getPassword();
            String incomingPassword = user.getPassword();

            if (passwordEncoder.matches(incomingPassword, encodedPassword)) {
                System.out.println("inloggad");
                adminService.saveLoginTime(existingUser);
                @SuppressWarnings("deprecation")
                String token = Jwts.builder()
                        .setSubject(existingUser.getId())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + jwtExpriationMs))
                        .signWith(SignatureAlgorithm.HS512, jwtSecret)
                        .compact();
                return ResponseEntity.ok(token);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Fel användarnamn eller lösenord");
    }

    @PostMapping("/logoutuser/{id}")
    public ResponseEntity<?> logout(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user != null) {
            adminService.saveLogoutTime(user);

            System.out.println("Utloggning för användare: " + id);
            return ResponseEntity.ok("Utloggad");
        } else {
            System.out.println("Användaren finns inte: " + id);
            return ResponseEntity.badRequest().body("Användaren finns inte");
        }
    }

    @PostMapping("/loginadmin")
    public ResponseEntity<?> loginadmin(@RequestBody Admin admin) {
        Admin existingAdmin = adminService.getAdminByUsername(admin.getUsername());

        if (existingAdmin != null) {
            String encodedPassword = existingAdmin.getPassword();
            String incomingPassword = admin.getPassword();

            if (passwordEncoder.matches(incomingPassword, encodedPassword)) {
                System.out.println("inloggad");
                @SuppressWarnings("deprecation")
                String token = Jwts.builder()
                        .setSubject(existingAdmin.getId())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + jwtExpriationMs))
                        .signWith(SignatureAlgorithm.HS512, jwtSecret)
                        .compact();
                return ResponseEntity.ok(token);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Fel användarnamn eller lösenord");
    }

    @PostMapping("/logoutadmin/{id}")
    public ResponseEntity<?> logoutadmin(@PathVariable String id) {
        Admin admin = adminService.getAdminById(id);
        if (admin != null) {

            System.out.println("Utloggning för admin: " + id);
            return ResponseEntity.ok("Utloggad");
        } else {
            System.out.println("admin finns inte: " + id);
            return ResponseEntity.badRequest().body("admin finns inte");
        }
    }
}