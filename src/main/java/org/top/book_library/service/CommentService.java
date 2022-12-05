package org.top.book_library.service;

import org.top.book_library.db.entity.Book;
import org.top.book_library.db.entity.Comment;

import java.util.List;

public interface CommentService {


    List<Comment> findAllComments(Book book);

    void deleteComment(Comment comment);

    void deleteCommentID(Long id);

    void addOrSaveComment(Comment comment);
}
