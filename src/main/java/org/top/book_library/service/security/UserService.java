package org.top.book_library.service.security;

import org.springframework.data.domain.Page;
import org.top.book_library.db.entity.security.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // получить весь список пользователей
    List<User> listAll();

    // добавление нового пользователя
    boolean addUser(User user);

    // найти пользователя по имени
    User findByUsername(String username);

    // найти пользователя по Id
    Optional<User> getById(Long id);

    // изменить права пользователя
    void updateUser(User user);

    // удалить пользователя по id
    void deleteUserById(Long id);

    // нумерация страниц(пагинация)
    Page<User> findPaginated(int pageNo, int size);
}
