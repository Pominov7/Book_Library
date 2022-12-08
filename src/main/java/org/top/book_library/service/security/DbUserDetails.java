package org.top.book_library.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.top.book_library.db.entity.security.User;

import java.util.Collection;
import java.util.Collections;

public class DbUserDetails implements UserDetails {

    // оборачиваем обычного пользователя
    private final User dbUser;                         // пользователь из базы данных

    public DbUserDetails(User dbUser) {
        this.dbUser = dbUser;
    }


    // метод добавления ролей
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.<GrantedAuthority>singletonList(
                new SimpleGrantedAuthority(dbUser.getRole().getRoleName())
        );
    }

    @Override
    public String getPassword() {
        return dbUser.getPassword();
    }

    @Override
    public String getUsername() {
        return dbUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}