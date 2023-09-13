package com.cycle.rental.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

 @Configuration
 @EnableWebSecurity
 public class WebSecurity {

    

        /**
         * @param http
         * @return
         * @throws Exception
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .cors().disable()
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .formLogin(login -> login.disable());
                return http.build();
        }

        // Possibly more bean methods ...
 }