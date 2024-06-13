package com.my.bubble.controller;

import com.my.bubble.user.model.User;
import com.my.bubble.user.model.request.RequestLogin;
import com.my.bubble.user.model.request.RequestUserJoin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping("/")
    public String main() {
        return "/main";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("user", new User());
        return "/join";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new RequestLogin());
        return "/login";
    }

    @PostMapping("/login")
    public String loginPost(@Valid @ModelAttribute("login") RequestLogin login, BindingResult bindingResult, Model model, HttpServletRequest request) {
        AuthenticationException exception = (AuthenticationException) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (exception instanceof AuthenticationException) {
            bindingResult.reject("bad", exception.getMessage());
        }

        if (bindingResult.hasErrors()) {
            return "/login";
        }

        model.addAttribute("login", new RequestLogin());
        return "forward:/login-process";
    }


//    @GetMapping("/join")
//    public String join2(RequestUserJoin requestUserJoin) {
//        AuthenticationManager object = authenticationManagerBuilder.getObject();
//
//        Authentication authenticate = object.authenticate(new UsernamePasswordAuthenticationToken(requestUserJoin.getId(), requestUserJoin.getPassword()));
//        authenticate.getAuthorities().to
//
//        return "/join";
//    }

}
