package org.top.book_library.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.top.book_library.db.entity.Author;

import java.util.Optional;
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // найти автора по имени
    Optional<Author> findByName(String name);
}
