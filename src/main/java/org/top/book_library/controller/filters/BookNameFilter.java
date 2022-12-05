package org.top.book_library.controller.filters;

import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Book;
import org.top.book_library.service.BookService;

import java.util.List;

@Service
public class BookNameFilter {
    private String match = "";   // строка фильтра

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public List<Book> getFilteredBooks(BookService service) {
        // фильтрация книг на основе включения match в название книги
        return service.findByContains(match);
    }
}
