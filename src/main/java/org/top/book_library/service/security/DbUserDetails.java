package org.top.book_library.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.top.book_library.db.entity.security.Authority;
import org.top.book_library.db.entity.security.User;
import java.util.Collection;
import java.util.stream.Stream;

public class DbUserDetails implements UserDetails {
    private final User dbUser;

    public DbUserDetails(User dbUser) {
        this.dbUser = dbUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //набор прав в строку
        Stream<String> stream = dbUser.getRole().getAuthorities().stream().map(Authority::getAuthorityName);
        //добавить в стрим роль и преобразовать все элементы в SimpleGrantedAuthority
        //вернуть в виде списка
        return Stream.concat(stream, Stream.of(dbUser.getRole().getRoleName()))
                .map(SimpleGrantedAuthority::new).toList();
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