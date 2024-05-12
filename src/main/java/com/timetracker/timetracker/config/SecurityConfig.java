package com.timetracker.timetracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.timetracker.timetracker.services.JpaUserDetailsService;

@Configuration
public class SecurityConfig {

    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService, JwtTokenFilter jwtTokenFilter) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class))
                .userDetailsService(jpaUserDetailsService)
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true));

        return http.build();
    }
}