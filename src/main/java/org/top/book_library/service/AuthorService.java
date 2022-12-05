package org.top.book_library.service;

import org.top.book_library.db.entity.Author;
import org.top.book_library.db.entity.Book;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    // Найти автора по строке
    Author findByName(String authorName);

    // Получение автора по id
     Optional<Author> getById(Long id);

    // получить всех авторов
    List<Author> listAllAuthors();

    // сохранить автора в БД
    Author saveAuthor(Author author);

    // Редактирование полей автора
    void updateAuthor(Author author);

    // удалить автора по id
    void deleteAuthorByID(Long id);

    List<Author> findByContainsNameAuthor(String match);

}
