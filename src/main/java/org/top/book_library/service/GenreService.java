package org.top.book_library.service;

import org.top.book_library.db.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    // получить жанра по id
    Optional<Genre> getById(Long id);

    // получить список всех жанров
    List<Genre> listAllGenres();

    // сохранить жанр
    Genre saveGenre(Genre genre);

    // редактировать поля жанра
    void updateGenre(Genre genre);

    // удалить жанр по id
    void deleteGenreByID(Long id);

    // найти жанр по содержимому строки
    List<Genre> findByContainsNameGenre(String match);
}
