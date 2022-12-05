package org.top.book_library.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.top.book_library.db.entity.Book;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    @Transactional
    @Modifying
    @Query(value = "UPDATE book_t SET genre_id=NULL WHERE genre_id=?1", nativeQuery = true)
    int clearGenreInBook(long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE book_t SET author_id=NULL WHERE  author_id=?1", nativeQuery = true)
    int clearAuthorInBook(long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE book_t SET link_id=NULL WHERE link_id=?1", nativeQuery = true)
    int clearLinkInBook(long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE book_t SET cover_id=NULL WHERE cover_id=?1", nativeQuery = true)
    int clearCoverInBook(long id);


}
