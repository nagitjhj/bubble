package com.my.bubble.user.repository;

import com.my.bubble.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {
    void save(User user);
    Optional<User> findById(String id);
//    User findById(String id);
    void updateLoginFailed(String id);
    void updateLoginFailedZero(String id);

    void saveLoginLog(String id);
}
