package org.top.book_library.service;

import org.top.book_library.db.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    // Получить автора по id
    Optional<Author> getById(Long id);

    // получить список всех авторов
    List<Author> listAllAuthors();

    // сохранить автора в БД
    Author saveAuthor(Author author);

    // Редактировать поля автора
    void updateAuthor(Author author);

    // удалить автора по id
    void deleteAuthorByID(Long id);

    // найти автора по содержимому строки
    List<Author> findByContainsNameAuthor(String match);

}
