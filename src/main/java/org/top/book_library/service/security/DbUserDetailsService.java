package org.top.book_library.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.security.User;
import org.top.book_library.db.repository.security.UserRepository;

@Service
public class DbUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // получить пользователя по имени
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. получить пользователя
        User user = userRepository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException(username);

        // 2. вернуть объект UserDetails
        return new DbUserDetails(user);
    }

}