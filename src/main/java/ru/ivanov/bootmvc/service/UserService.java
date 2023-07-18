package ru.ivanov.bootmvc.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ivanov.bootmvc.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    User getUserById(long id);

    void updateUser(User updateUser);

    void removeUserById(long id);

    void save(User user);
}