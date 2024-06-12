package com.my.bubble.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(authorize -> authorize.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/join").permitAll()
                        .anyRequest().authenticated()
                );

        http
                .httpBasic(withDefaults())
        ;

        http
                .formLogin(f->f
                        .usernameParameter("id")
                        .passwordParameter("password")
//                        .loginPage(withDefaults())
                );

        return http.build();
    }

}
