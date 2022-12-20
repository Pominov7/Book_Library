package org.top.book_library.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.top.book_library.db.entity.Genre;
import org.top.book_library.db.repository.GenreRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    private static final Long GENRE_ID = 1L;
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private GenreService genreService;


    @Test
    void getById() {
        doReturn(Optional.of(new Genre(GENRE_ID)))
                .when(genreRepository).getById(GENRE_ID);
        var actualResult = genreService.getById(GENRE_ID);
        assertTrue(actualResult.isPresent());

        var expectedResult = new Genre(GENRE_ID);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));

    }

    @Test
    void listAllGenres() {
    }

    @Test
    void saveGenre() {
    }

    @Test
    void updateGenre() {
    }

    @Test
    void deleteGenreByID() {
    }

    @Test
    void findByContainsNameGenre() {
    }

    @Test
    void findPaginated() {
    }
}