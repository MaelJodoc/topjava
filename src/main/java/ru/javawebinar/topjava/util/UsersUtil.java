package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Смена on 11.03.2018.
 */
public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(1, "Hubert", "Hubert@gmail.com", "12345", Role.ROLE_USER),
            new User(2, "Amigo", "Amigo@brouser.ru", "12345", Role.ROLE_ADMIN),
            new User(3, "Bender", "Bender@killall.humans", "123", Role.ROLE_ADMIN, Role.ROLE_USER),
            new User(4, "Lily", "Lily@gmail.com", "qwerty", Role.ROLE_USER)
    );
}
