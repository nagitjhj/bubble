package com.my.bubble.user.controller;

import com.my.bubble.user.model.User;
import com.my.bubble.user.model.request.RequestUserJoin;
import com.my.bubble.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public String join(@ModelAttribute RequestUserJoin user) {

        userService.save(user);

        return "redirect:/login";
    }

    @GetMapping("/user")
    public String main() {
        return "/user/main";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin/main";
    }
}
