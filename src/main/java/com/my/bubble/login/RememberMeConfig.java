package com.my.bubble.login;

import com.my.bubble.login.auth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

//@Configuration
@RequiredArgsConstructor
public class RememberMeConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

//    @Bean
//    RememberMeServices rememberMeServices(PersistentTokenRepository persistentTokenRepository) {
//        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
//        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("myKey", new UserDetailsServiceI, persistentTokenRepository);
//        rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
//        return rememberMe;
//    }

    @Bean
    public org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices rememberMeServices() {
        org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices rememberMeServices =
                new PersistentTokenBasedRememberMeServices("myKey", customUserDetailsService, persistentTokenRepository());
        rememberMeServices.setTokenValiditySeconds(100);
        return rememberMeServices;
    }
}
