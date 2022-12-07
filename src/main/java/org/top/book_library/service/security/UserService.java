package org.top.book_library.service.security;

import org.top.book_library.db.entity.security.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // получить весь список пользователей
    List<User> listAll();

    // добавление нового пользователя
    boolean addUser(User user);

    // поиск пользователя по имени
    User findByUsername(String username);

    // поиск пользователя по Id
    Optional<User> getById(Long id);

    // изменить права пользователя
    void updateUser(User user);

    // удалить пользователя по id
    void deleteUserById(Long id);
}
