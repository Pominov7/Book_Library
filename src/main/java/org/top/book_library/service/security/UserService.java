package org.top.book_library.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.security.User;
import org.top.book_library.db.repository.security.RoleRepository;
import org.top.book_library.db.repository.security.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // пароль надо хэшировать
        user.setRole(roleRepository.findByRoleName("ROLE_USER"));
        userRepository.save(user);   // сохранил пользователя

        return true;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // удалить юзера по id
    public void deleteUserById(Long id) {
        // 1. найти юзера для удаления
        Optional<User> deleted = userRepository.findById(id);
        // 2. если такой юзер есть, то удалить его
        deleted.ifPresent(user -> userRepository.delete(user));
    }
}
