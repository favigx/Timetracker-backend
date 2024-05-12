package com.timetracker.timetracker.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.timetracker.timetracker.models.User;
import com.timetracker.timetracker.models.UserDto;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JpaUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Hittade ingen användare");
        }
        return new UserDto(user);
    }
}