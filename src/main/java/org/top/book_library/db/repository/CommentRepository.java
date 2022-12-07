package org.top.book_library.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.top.book_library.db.entity.Book;
import org.top.book_library.db.entity.Comment;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBook(Book book);

    @Transactional
    @Modifying
    @Query(value = "UPDATE comment SET user_id=NULL WHERE user_id=?1", nativeQuery = true)
    int clearUserInComment(long id);

}