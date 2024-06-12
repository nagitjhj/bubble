package com.my.bubble.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class User {
    private String id;
    private String password;
//    private String role;
    private String auth;
//    private List<Auth> authList;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public List<String> getAuthList() {
        if(auth != null) {
            return Arrays.asList(auth.split(","));
        }
        return null;
    }
}
