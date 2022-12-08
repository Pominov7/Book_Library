package org.top.book_library.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.top.book_library.db.entity.Link;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    // найти ссылку по названию
    Optional<Link> findByNameLink(String name);

}
