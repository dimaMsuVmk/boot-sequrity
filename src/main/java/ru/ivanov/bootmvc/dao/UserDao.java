package ru.ivanov.bootmvc.dao;

import ru.ivanov.bootmvc.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User getUserById(long id);

    void updateUser(User updateUser);

    void removeUserById(long id);

    void save(User user);
}