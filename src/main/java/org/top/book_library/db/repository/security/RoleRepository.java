package org.top.book_library.db.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.top.book_library.db.entity.security.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // найти роль по названию
    Role findByRoleName(String roleName);
}
