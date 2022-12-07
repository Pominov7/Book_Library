package org.top.book_library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigWebSecurity {
    // зависимость кодировщика паролей
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/books/addBook", "/books/delete/**", "/books/edit/**",
                                 "/covers/addCover", "/covers/delete/**", "/covers/edit/**",
                                "/genres/addGenre", "/genres/delete/**", "/genres/edit/**", "/authors/addAuthor",
                                "/authors/delete/**", "/authors/edit/**", "/links/addLink", "/links/delete/**",
                                "/links/edit/**","/comments/delete/**","/users/edit/**").hasRole("ADMIN")
//                        .antMatchers("/books", "/books/details/**").hasAnyRole("USER")
                        .antMatchers("/","/books","/genres","/authors","/index", "/registration", "/webjars/**", "/css/**", "/img/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout().logoutSuccessUrl("/").and().csrf().disable();
        return http.build();
    }

}
