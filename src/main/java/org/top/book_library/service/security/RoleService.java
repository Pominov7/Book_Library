package org.top.book_library.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.security.Role;
import org.top.book_library.db.repository.security.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> listAll() {
        return (List<Role>) roleRepository.findAll();
    }
}