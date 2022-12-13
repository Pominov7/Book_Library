package org.top.book_library.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.top.book_library.db.entity.Book;
import org.top.book_library.db.entity.Comment;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // найти комментарий по книги
    List<Comment> findAllByBook(Book book);

    // Очистка поля пользователя, написавшего комментарий, при удалении пользователя
    @Transactional
    @Modifying
    @Query(value = "UPDATE comment SET user_id=NULL WHERE user_id=?1", nativeQuery = true)
    int clearUserInComment(long id);


    // получить количество комментариев к книге
    @Query("select count(c.id) from Comment c where c.book.id=:bookId")
    Integer getTheNumberOfCommentsOnTheBook(@Param("bookId") Long bookId);
}