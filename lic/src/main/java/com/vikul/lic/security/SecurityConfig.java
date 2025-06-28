package com.vikul.lic.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
