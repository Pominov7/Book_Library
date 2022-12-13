package org.top.book_library.service;

import org.top.book_library.db.entity.Book;
import org.top.book_library.db.entity.Comment;

import java.util.List;

public interface CommentService {

    // найти все комментарии определенной книги
    List<Comment> findAllComments(Book book);

    // удалить комментарий
    void deleteComment(Comment comment);

    // сохранить комментарий
    void addOrSaveComment(Comment comment);

    // получить количество комментариев к книге
    Integer getTheNumberOfCommentsOnTheBook(Long bookId);
}
