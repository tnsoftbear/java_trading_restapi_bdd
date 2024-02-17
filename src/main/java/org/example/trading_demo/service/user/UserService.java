package org.example.trading_demo.service.user;

import org.example.trading_demo.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User saveUser(User user);
    User findByUsername(String username);
    User updateUser(User user);
    void deleteUser(String username);
}
