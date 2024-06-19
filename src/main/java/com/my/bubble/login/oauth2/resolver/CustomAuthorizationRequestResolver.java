package com.my.bubble.login.oauth2.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    private final OAuth2AuthorizationRequestResolver defaultResolver;

    public CustomAuthorizationRequestResolver(ClientRegistrationRepository repository) {
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(
                repository, "/oauth2/authorization"
        );
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest authorizationRequest =
                this.defaultResolver.resolve(request);

        return authorizationRequest != null ?
                customAuthorizationRequest(request, authorizationRequest) :
                null;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest authorizationRequest =
                this.defaultResolver.resolve(request, clientRegistrationId);

        return authorizationRequest != null ?
                customAuthorizationRequest(request, authorizationRequest) :
                null;
    }

    private OAuth2AuthorizationRequest customAuthorizationRequest(HttpServletRequest request, OAuth2AuthorizationRequest authorizationRequest) {
        HttpSession session = request.getSession();
        Boolean hasLoggedIn = (Boolean) session.getAttribute("HAS_LOGGED_IN");

        Map<String, Object> additionalParameters = new LinkedHashMap<>(authorizationRequest.getAdditionalParameters());
        if (Boolean.TRUE.equals(hasLoggedIn)) { //Boolean null check
            additionalParameters.put("prompt", "none");
            additionalParameters.put("remember", "true");
        } else {
            additionalParameters.put("prompt", "select_account");
            additionalParameters.put("remember", "true");
        }

        return OAuth2AuthorizationRequest.from(authorizationRequest)
                .additionalParameters(additionalParameters)
                .build();
    }
}
