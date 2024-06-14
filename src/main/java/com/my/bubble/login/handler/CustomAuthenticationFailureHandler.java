package com.my.bubble.login.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final String forwardUrl = "/login";

//    public CustomAuthenticationFailureHandler() {
//        Assert.isTrue(UrlUtils.isValidRedirectUrl(forwardUrl), () -> forwardUrl + "존재 ㄴㄴ");
//        this.forwardUrl = forwardUrl;
//    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        request.getRequestDispatcher(forwardUrl).forward(request, response);
//        response.sendRedirect("/login");
    }
}
