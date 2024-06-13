package com.my.bubble.config;

import com.my.bubble.config.handler.CustomAuthenticationFailureHandler;
import com.my.bubble.config.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(authorize -> authorize.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/join", "/login").permitAll()
                        .anyRequest().authenticated()
                );

//        http
//                .httpBasic(withDefaults())
//        ;
        http
                .formLogin(f->f
                        .loginPage("/login")
                        .loginProcessingUrl("/login-process")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                );

        return http.build();
    }

}
