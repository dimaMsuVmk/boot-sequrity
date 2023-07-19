package ru.ivanov.bootmvc.util;

import ru.ivanov.bootmvc.model.Role;
public class RoleUtil {
    public static final Role USER = new Role("ROLE_USER",1l);
    public static final Role ADMIN = new Role("ROLE_ADMIN",2l);
    public static final Role GUEST = new Role("ROLE_GUEST",3l);
}
