package org.example.trading_demo.service.user.impl;

import lombok.AllArgsConstructor;
import org.example.trading_demo.model.User;
import org.example.trading_demo.repository.user.H2UserRepository;
import org.example.trading_demo.service.user.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@AllArgsConstructor
public class H2UserServiceImpl implements UserService {

    private final H2UserRepository repo;

    public List<User> findAllUsers() {
        return repo.findAll();
    }

    @Override
    public User saveUser(User user) {
        return repo.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public User updateUser(User user) {
        return repo.save(user);
    }

    @Override
    public void deleteUser(String username) {
        User user = repo.findByUsername(username);
        if (user != null) {
            repo.delete(user);
        }
    }
}