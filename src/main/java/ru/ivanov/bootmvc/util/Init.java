package ru.ivanov.bootmvc.util;

import org.springframework.stereotype.Component;
import ru.ivanov.bootmvc.model.Role;
import ru.ivanov.bootmvc.model.User;
import ru.ivanov.bootmvc.repository.RoleRepository;
import ru.ivanov.bootmvc.service.UserService;

@Component
public class Init {
    public static final Role USER = new Role("ROLE_USER");
    public static final Role ADMIN = new Role("ROLE_ADMIN");
    public static final Role GUEST = new Role("ROLE_GUEST");
    private final UserService userService;
    private final RoleRepository roleRepository;

    public Init(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    public void initilize() {
        roleRepository.save(USER);
        roleRepository.save(ADMIN);
        roleRepository.save(GUEST);
        User user = new User("user","Ivanov","user@bk.ru","user");
        user.getRoles().add(USER);
        User admin = new User("admin","Rublev","admin@bk.ru","admin");
        admin.getRoles().add(ADMIN);
        userService.save(user);
        userService.save(admin);

    }
}
