package org.top.book_library.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.top.book_library.db.entity.Cover;


import java.util.Optional;

@Repository
public interface CoverRepository extends JpaRepository<Cover, Long> {
    Optional<Cover> findByNameCover(String name);
}
