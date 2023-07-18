package ru.ivanov.bootmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.bootmvc.dao.UserDao;
import ru.ivanov.bootmvc.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public void updateUser(User updateUser) {
        userDao.updateUser(updateUser);
    }

    @Transactional
    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    @Transactional
    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //findByUsername(...) - бросит ошибку, если такого user нет в базе
        try {
            Optional<User> user = userDao.findByUsername(username);
            //при использовании DATA JPA findByUsername() вернет Optional<null>
            //соответственно можно проверить на user.isEmpty() и убрать try/catch
            //if (user.isEmpty()) throw new UsernameNotFoundException("User with given userName not found !");
            return user.get();
        }catch (Exception e){
            throw new UsernameNotFoundException("User with given userName > 1 OR user not found !");
        }
    }
}