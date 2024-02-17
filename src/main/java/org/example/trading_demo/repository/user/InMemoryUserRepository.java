package org.example.trading_demo.repository.user;

import org.example.trading_demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryUserRepository {
    private List<User> userStorage = new ArrayList<User>();

    public List<User> findAllUsers() {
        return userStorage;
    }

    public User saveUser(User user) {
        userStorage.add(user);
        return user;
    }

    public User findByUsername(String username) {
        for (User user : userStorage) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User updateUser(User targetUser) {
        for (int index = 0; index < userStorage.size(); index++) {
            User user = userStorage.get(index);
            if (user.getId() == targetUser.getId()) {
                userStorage.set(index, targetUser);
                return targetUser;
            }
        }
        return null;
    }

    public void deleteUser(String username) {
        var deletingUser = findByUsername(username);
        if (deletingUser != null) {
            userStorage.remove(deletingUser);
        }
    }

    public static class TradeRepository {
    }
}
