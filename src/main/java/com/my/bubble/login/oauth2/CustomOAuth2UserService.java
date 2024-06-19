package com.my.bubble.login.oauth2;

import com.my.bubble.login.auth.CustomUserDetails;
import com.my.bubble.user.model.User;
import com.my.bubble.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
//    private final PersistentTokenRepository tokenRepository;
//    private final PersistentTokenBasedRememberMeServices rememberMeServices;

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

        //set remember me cookie
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        rememberMeServices.loginSuccess(request, response, authentication);

        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }
}
