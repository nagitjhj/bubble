package com.my.bubble.controller;

import com.my.bubble.user.model.User;
import com.my.bubble.user.model.request.RequestUserJoin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
