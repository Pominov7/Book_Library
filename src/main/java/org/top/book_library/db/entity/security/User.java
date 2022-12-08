package org.top.book_library.db.entity.security;

import javax.persistence.*;

// таблица пользователей
@Entity
@Table(name="user_t")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;                    // уникальный ненулевой логин пользователя
    @Column(nullable = false)
    private String password;                    // пароль пользователя
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;                          // роль пользователя

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return id + " - " + username;
    }

}