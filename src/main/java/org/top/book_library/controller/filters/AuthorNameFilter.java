package org.top.book_library.controller.filters;

import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Author;
import org.top.book_library.db.entity.Book;
import org.top.book_library.service.AuthorService;
import org.top.book_library.service.BookService;

import java.util.List;
@Service
public class AuthorNameFilter {
    private String match = "";   // строка фильтра

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public List<Author> getFilteredAuthors(AuthorService service) {
        // фильтрация автора на основе включения match в имя и фамилию автора
        return service.findByContainsNameAuthor(match);
    }
}
