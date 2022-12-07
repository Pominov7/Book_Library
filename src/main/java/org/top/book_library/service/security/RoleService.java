package org.top.book_library.service.security;

import org.top.book_library.db.entity.security.Role;

import java.util.List;

public interface RoleService {

    // получить список всех ролей
    public List<Role> listAll();
}
