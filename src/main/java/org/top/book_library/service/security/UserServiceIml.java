package org.top.book_library.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.security.User;
import org.top.book_library.db.repository.security.RoleRepository;
import org.top.book_library.db.repository.security.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceIml implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // получить весь список пользователей
    @Override
    public List<User> listAll() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparing(User::getUsername))
                .collect(Collectors.toList());
    }

    // добавление нового пользователя
    @Override
    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // хэшируем пароль
        user.setRole(roleRepository.findByRoleName("ROLE_USER")); // новому пользователю присвоим права USER
        userRepository.save(user);   // сохраним пользователя

        return true;
    }

    // найти пользователя по имени
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // найти пользователя по Id
    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    // изменить права пользователя
    @Override
    public void updateUser(User user) {
        Optional<User> optionalUser = getById(user.getId());
        if (optionalUser.isPresent()) {
            User editedUser = optionalUser.get();
            if (!editedUser.equals(user)) {
                editedUser.setRole(user.getRole());
                userRepository.save(editedUser);
            }
        }
    }


    // удалить пользователя по id
    @Override
    public void deleteUserById(Long id) {
        // 1. найти пользователя для удаления
        Optional<User> deleted = userRepository.findById(id);
        // 2. если такой есть, то удалить его
        deleted.ifPresent(user -> userRepository.delete(user));
    }

    // нумерация страниц(пагинация)
    @Override
    public Page<User> findPaginated(int pageNo, int size) {
        Pageable pageable = PageRequest.of(pageNo - 1, size);
        return userRepository.findAll(pageable);
    }
}
