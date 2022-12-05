package org.top.book_library.db.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.top.book_library.db.entity.security.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(String roleName);
}
