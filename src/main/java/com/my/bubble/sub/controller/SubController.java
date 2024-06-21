package com.my.bubble.sub.controller;

import com.my.bubble.login.auth.CustomUserDetails;
import com.my.bubble.sub.model.RequestSub;
import com.my.bubble.sub.model.ResponsePubList;
import com.my.bubble.sub.model.ResponseSubList;
import com.my.bubble.sub.service.SubService;
import com.my.bubble.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SubController {
    private final SubService subService;

    @GetMapping("/pub/list")
    public String pubListPage(Model model, Principal principal) {
        List<ResponsePubList> pubList = subService.findPubList(principal.getName());

        model.addAttribute("pubList", pubList);

        return "sub/pub-list";
    }

    @ResponseBody
    @PostMapping("/sub")
    public ResponseEntity<Map<String, String>> sub(@RequestBody RequestSub requestSub, @AuthenticationPrincipal CustomUserDetails user, Principal principal) {
        Map<String, String> response = new HashMap<>();
//        if (user instanceof CustomUserDetails) {
//            CustomUserDetails
//        }

        try {
            subService.subFirst(principal.getName(), requestSub.getPubId());
            response.put("message", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/sub/list")
    public String subList(Model model, Principal principal) {
        List<ResponseSubList> subList = subService.subList(principal.getName());
        model.addAttribute("subList", subList);
        return "sub/sub-list";
    }
}
