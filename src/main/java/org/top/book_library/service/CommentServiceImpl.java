package org.top.book_library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Book;
import org.top.book_library.db.entity.Comment;
import org.top.book_library.db.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAllComments(Book book) {
        return commentRepository.findAllByBook(book);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void addOrSaveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentID(Long id) {
        Optional<Comment> result = commentRepository.findById(id);
        result.ifPresent(commentRepository::delete);
    }
}