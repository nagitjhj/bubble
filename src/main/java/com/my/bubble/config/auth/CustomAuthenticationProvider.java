package com.my.bubble.config.auth;

import com.my.bubble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
//    @Transactional 이거 있으면 안됨 이유를 알아야... 미스테리...
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString(); //비번인가? 뭐람

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        if (!userDetails.isAccountNonLocked()) {
            throw new BadCredentialsException("계정 lock.. so bad cheer up!!");
        }

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            if (userDetails.getLoginFailed() > 0) {
                userRepository.updateLoginFailedZero(username);
            }
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        } else {
            userRepository.updateLoginFailed(username);
            throw new BadCredentialsException("안됩ㄴ디ㅏ. 비번이 다릅니다ㅣ");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //UsernamePasswordAuthenticationToken 사용자 이름과 암호를 이용하는 표준 인증
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
