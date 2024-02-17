package org.example.trading_demo.service.user.impl;

import lombok.AllArgsConstructor;
import org.example.trading_demo.model.User;
import org.example.trading_demo.repository.user.InMemoryUserRepository;
import org.example.trading_demo.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InMemoryUserServiceImpl implements UserService {

    private final InMemoryUserRepository repo;

    public List<User> findAllUsers() {
        return repo.findAllUsers();
    }

    @Override
    public User saveUser(User user) {
        return repo.saveUser(user);
    }

    @Override
    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public User updateUser(User user) {
        return repo.updateUser(user);
    }

    @Override
    public void deleteUser(String username) {
        repo.deleteUser(username);
    }
}