package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    User findUserById(Long userId);

    List<User> allUsers();

    void saveUser(User user);

    void editUser(User user);

    boolean deleteUser(Long userId);

    User getByUserName(String name);
}
