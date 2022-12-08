package org.top.book_library.controller.filters;

import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Genre;
import org.top.book_library.service.GenreService;

import java.util.List;
@Service
public class GenreNameFilter {

    private String match = "";   // строка фильтра

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public List<Genre> getFilteredGenres(GenreService service) {
        // фильтрация жанра на основе включения match в название жанра
        return service.findByContainsNameGenre(match);
    }


}
