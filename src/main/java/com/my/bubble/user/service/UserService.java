package com.my.bubble.user.service;

import com.my.bubble.user.model.User;
import com.my.bubble.user.model.request.RequestUserJoin;
import com.my.bubble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public void save(RequestUserJoin requestUserJoin) {
        User user = new User(requestUserJoin.getId(), encoder.encode(requestUserJoin.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void updateLoginFailed(String id) {
        userRepository.updateLoginFailed(id);
    }

    @Transactional
    public void updateLoginFailedZero(String id) {
        userRepository.updateLoginFailedZero(id);
    }

    @Transactional
    public void saveLoginLog(String id) {
        userRepository.saveLoginLog(id);
    }

}
