package org.top.book_library.service;

import org.top.book_library.db.entity.Book;
import org.top.book_library.db.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    // Найти жанр по названию
    Genre findByName(String genreName);

    // Получение жанра по id
    Optional<Genre> getById(Long id);

    // получить список всех жанров
    List<Genre> listAllGenres();

    // сохранить жанр
    Genre saveGenre(Genre genre);

    // удалить жанр по id
    void deleteGenreByID(Long id);
}
