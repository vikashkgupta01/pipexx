package com.vikul.lic.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ✅ disable CSRF for stateless APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/lic/v1/**").permitAll() // Public endpoints
                        .anyRequest().authenticated()             // All other endpoints require auth
                )
                .httpBasic(Customizer.withDefaults()); // ✅ Basic Auth

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1= User.withUsername("vikul")
                .password("{noop}Vikash@123")
                .roles("ADMIN")
                .build();


        UserDetails user2= User.withUsername("vikash")
                .password("{noop}Vikash@321")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

}
