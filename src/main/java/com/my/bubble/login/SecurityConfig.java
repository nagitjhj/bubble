package com.my.bubble.login;

import com.my.bubble.login.auth.CustomUserDetailsService;
import com.my.bubble.login.handler.CustomAuthenticationFailureHandler;
import com.my.bubble.login.handler.CustomAuthenticationSuccessHandler;
import com.my.bubble.login.oauth2.CustomOAuth2UserService;
import com.my.bubble.login.oauth2.resolver.CustomAuthorizationRequestResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.sql.DataSource;

import static org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;
    private final DefaultOAuth2UserService oauth2UserService;
    private final ClientRegistrationRepository clientRegistrationRepository; //디폴트가 InMemory....
//    private final RememberMeServices rememberMeServices;

    private final CustomUserDetailsService customUserDetailsService;
    private final DataSource dataSource;
//    private final PersistentTokenRepository tokenRepository;
//    private final PersistentTokenBasedRememberMeServices rememberMeServices;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(authorize -> authorize.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/join", "/login").permitAll()
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/cel/**").hasAnyRole("ADMIN", "CEL")
                        .anyRequest().permitAll()
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
//                        .authorizationEndpoint(auth->auth
//                                .authorizationRequestResolver(new CustomAuthorizationRequestResolver(clientRegistrationRepository))
//                        )
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                );

//        http
//                .rememberMe(re->re
//                        .rememberMeParameter("remember")
//                        .tokenValiditySeconds(100)
//                        .tokenRepository(tokenRepository)
//                        .rememberMeServices(rememberMeServices)
//                );

        http
                .logout(l->l
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
//                        .deleteCookies("J")
                );

        return http.build();
    }



}
