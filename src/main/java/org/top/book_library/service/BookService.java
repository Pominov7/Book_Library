package org.top.book_library.service;

import org.top.book_library.db.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    // Найти книгу по строке
    Book findByTitle(String title);

    // Получение автора по id
     Optional<Book> getById(Long id);

    // получить все книги
    List<Book> listAllBooks();

    // добавить и сохранить книгу
    void saveBook(Book book);

    // редактирование полей книги
    void updateBook(Book book);

    // удалить книгу по id
    void deleteBookByID(Long id);



}