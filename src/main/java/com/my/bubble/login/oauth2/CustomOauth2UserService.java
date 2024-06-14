package com.my.bubble.login.oauth2;

import com.my.bubble.login.auth.CustomUserDetails;
import com.my.bubble.user.model.User;
import com.my.bubble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    //소셜 로그인 후처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String username = oAuth2User.getAttribute("email");

        User user = userRepository.findById(username).orElse(null);

        if (user == null) {
            user = new User(username
                    , passwordEncoder.encode("oauth")
                    , userRequest.getClientRegistration().getRegistrationId()
                    , oAuth2User.getAttribute("sub")
            );

            userRepository.saveOAuth2(user);
            userRepository.saveAuth(username);
            user = userRepository.findById(username).orElse(null);
        }

//        User userEntity = userRepository.findById(username).orElse(null);

        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }
}
