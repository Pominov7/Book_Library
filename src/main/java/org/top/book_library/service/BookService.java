package org.top.book_library.service;

import org.springframework.data.domain.Page;
import org.top.book_library.db.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    // Получить книгу по id
    Optional<Book> getById(Long id);

    // получить список всех книг
    List<Book> listAllBooks();

    // добавить и сохранить книгу
    void saveBook(Book book);

    // редактировать поля книги
    void updateBook(Book book);

    // удалить книгу по id
    void deleteBookByID(Long id);

    // получить книгу по содержимому строки
    List<Book> findByContains(String match);

    // получить список книг определенного автора
    List<Book> listBookAuthorId(Long id);

    // получить список книг определенного жанра
    List<Book> listBookGenreId(Long id);

    // нумерация страниц(пагинация)
    Page<Book> findPaginated(int pageNo, int size);
}