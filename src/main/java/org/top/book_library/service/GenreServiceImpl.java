package org.top.book_library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Genre;
import org.top.book_library.db.repository.GenreRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    // получить жанра по id
    @Override
    public Optional<Genre> getById(Long id) {
        return genreRepository.findById(id);
    }

    // получить список всех жанров
    @Override
    public List<Genre> listAllGenres() {
        return genreRepository.findAll().stream()
                .sorted(Comparator.comparing(Genre::getName))
                .collect(Collectors.toList());

    }

    // сохранить жанр
    @Override
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);

    }

    // редактировать поля жанра
    @Override
    public void updateGenre(Genre genre) {
        Optional<Genre> optionalGenre = getById(genre.getId());
        if (optionalGenre.isPresent()) {
            Genre editedGenre = optionalGenre.get();
            if (!editedGenre.equals(genre)) {
                editedGenre.setName(genre.getName());
                genreRepository.save(editedGenre);
            }
        }
    }

    // удалить жанр по id
    @Override
    public void deleteGenreByID(Long id) {
        Optional<Genre> result = genreRepository.findById(id);
        result.ifPresent(genreRepository::delete);
    }

    // найти жанр по содержимому строки
    @Override
    public List<Genre> findByContainsNameGenre(String match) {
        if (match == null || match.equals(""))
            return genreRepository.findAll();
        return genreRepository.findAll()
                .stream()
                .filter(s -> s.getName().toLowerCase().contains(match.toLowerCase()))
                .toList();
    }
}
