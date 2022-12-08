package org.top.book_library.service;

import org.top.book_library.db.entity.Genre;
import org.top.book_library.db.entity.Link;

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

    // редактирование полей жанра
    void updateGenre(Genre genre);

    // удалить жанр по id
    void deleteGenreByID(Long id);

    // Найти жанр по введенной строке
    List<Genre> findByContainsNameGenre(String match);
}
