package org.top.book_library.db.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.top.book_library.db.entity.security.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // найти пользователя по имени
    User findByUsername(String username);
}
