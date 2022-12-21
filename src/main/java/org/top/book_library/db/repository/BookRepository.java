package org.top.book_library.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.top.book_library.db.entity.Book;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Очистка поля жанра книги, при удалении жанра
    @Transactional
    @Modifying
    @Query(value = "UPDATE book_t SET genre_id=NULL WHERE genre_id=?1", nativeQuery = true)
    int clearGenreInBook(long id);

    // Очистка поля автора книги, при удалении автора
    @Transactional
    @Modifying
    @Query(value = "UPDATE book_t SET author_id=NULL WHERE  author_id=?1", nativeQuery = true)
    int clearAuthorInBook(long id);

    // Очистка поля ссылки книги, при удалении ссылки
    @Transactional
    @Modifying
    @Query(value = "UPDATE book_t SET link_id=NULL WHERE link_id=?1", nativeQuery = true)
    int clearLinkInBook(long id);

    // Очистка поля обложки книги, при удалении обложки
    @Transactional
    @Modifying
    @Query(value = "UPDATE book_t SET cover_id=NULL WHERE cover_id=?1", nativeQuery = true)
    int clearCoverInBook(long id);

}
