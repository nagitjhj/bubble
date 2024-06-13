package com.my.bubble.config.auth;

import com.my.bubble.user.model.User;
import com.my.bubble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    //user객체를 찾아서 넣는 역할만
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("잘못된 아이디"));

//        User user = userRepository.findById(username);

        if (user != null) {
            return new CustomUserDetails(user);
        }
        return null;
    }
}
