package org.top.book_library.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.top.book_library.db.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
