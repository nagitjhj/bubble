package com.my.bubble.login.auth;

import com.my.bubble.user.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class CustomUserDetails implements UserDetails, OAuth2User {
    private User user;
    private Map<String, Object> attribute; //아직 뭔지 모르고있음 - 알고있음

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public CustomUserDetails(User user, Map<String, Object> attribute) {
        this.user = user;
        this.attribute = attribute;
    }

    public Integer getLoginFailed() {
        return user.getLoginFailed();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(user);
        user.getAuthList().forEach(auth->authorities.add(()->auth));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getLockYn().equals("N")?true:false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        if(user.getLatestLoginDate() == null) return true;

        // 1년동안 로그인 안 하면 휴면계정
        LocalDateTime loginPlusOne = user.getLatestLoginDate().plusYears(1);
        LocalDateTime now = LocalDateTime.now();

        return loginPlusOne.isAfter(now);
    }

    @Override
    public String getName() {
        return "";
    }
}
