package com.my.bubble.user.repository;

import com.my.bubble.user.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    void save(User user);
    User findById(String id);
}
