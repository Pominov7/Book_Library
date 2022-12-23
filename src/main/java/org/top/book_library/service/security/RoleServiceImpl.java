package org.top.book_library.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.security.Role;
import org.top.book_library.db.repository.security.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    // получить список всех ролей
    @Override
    public List<Role> listAll() {
        return roleRepository.findAll();
    }

}