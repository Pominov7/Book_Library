package org.top.book_library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Book;
import org.top.book_library.db.entity.Genre;
import org.top.book_library.db.entity.Link;
import org.top.book_library.db.repository.GenreRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;


    // Найти жанр по названию
    @Override
    public Genre findByName(String genreName) {
        return genreRepository.findByName(genreName).orElse(null);
    }

    // Получение жанра по id
    @Override
    public Optional<Genre> getById(Long id) {
        return genreRepository.findById(id);
    }

    // получить список всех жанров
    @Override
    public List<Genre> listAllGenres() {
        return genreRepository.findAll();
    }

    // сохранить жанр
    @Override
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);

    }

    // удалить жанр по id
    @Override
    public void deleteGenreByID(Long id) {
        Optional<Genre> result = genreRepository.findById(id);
        result.ifPresent(genreRepository::delete);
    }
}
