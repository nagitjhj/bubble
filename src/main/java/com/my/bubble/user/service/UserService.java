package com.my.bubble.user.service;

import com.my.bubble.user.model.User;
import com.my.bubble.user.model.request.RequestUserJoin;
import com.my.bubble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public void save(RequestUserJoin requestUserJoin) {
        User user = new User(requestUserJoin.getId(), encoder.encode(requestUserJoin.getPassword()));
        userRepository.save(user);
    }
}
