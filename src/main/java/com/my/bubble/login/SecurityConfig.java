package com.my.bubble.login;

import com.my.bubble.login.handler.CustomAuthenticationFailureHandler;
import com.my.bubble.login.handler.CustomAuthenticationSuccessHandler;
import com.my.bubble.login.oauth2.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;
    private final CustomOauth2UserService oauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(authorize -> authorize.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/join", "/login").permitAll()
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/cel/**").hasAnyRole("ADMIN", "CEL")
                        .anyRequest().authenticated()
                );

        http
                .formLogin(f->f
                        .loginPage("/login")
                        .loginProcessingUrl("/login-process")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                );

        http
                .oauth2Login(o->o
                        .loginPage("/login")
                        .userInfoEndpoint(info->info
                                .userService(oauth2UserService)
                        )
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                );

        return http.build();
    }

}
