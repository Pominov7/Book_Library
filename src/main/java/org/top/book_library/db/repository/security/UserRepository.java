package org.top.book_library.db.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.top.book_library.db.entity.security.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
