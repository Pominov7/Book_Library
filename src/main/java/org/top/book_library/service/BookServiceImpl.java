package org.top.book_library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Author;
import org.top.book_library.db.entity.Book;
import org.top.book_library.db.entity.Genre;
import org.top.book_library.db.entity.Link;
import org.top.book_library.db.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;

    @Autowired
    private LinkService linkService;

    // найти книгу по названию
    @Override
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title).orElse(null);
    }

    // найти книгу по Id
    @Override
    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    // получить весь список книг
    @Override
    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    // сохранить книгу
    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    // изменить поля книги
    @Override
    public void updateBook(Book book) {
        Optional<Book> optionalBook = getById(book.getId());
        if (optionalBook.isPresent()) {
            Book editedBook = optionalBook.get();
            if (!editedBook.equals(book)) {
                editedBook.setTitle(book.getTitle());
                editedBook.setAuthor(book.getAuthor());
                editedBook.setGenre(book.getGenre());
                editedBook.setLink(book.getLink());
                bookRepository.save(editedBook);
            }
        }
    }

    // удалить книгу по Id
    @Override
    public void deleteBookByID(Long id) {
        Optional<Book> result = bookRepository.findById(id);
        result.ifPresent(bookRepository::delete);
    }

    // Получения книги по строке
    @Override
    public List<Book> findByContains(String match) {
        if (match == null || match.equals(""))
            return bookRepository.findAll();
        return bookRepository.findAll()
                .stream()
                .filter(s -> s.getTitle().contains(match))
                .toList();
    }

    // Вернуть список книг автора
    public List<Book> listBookAuthorId(Long id) {
        List<Book> books = bookRepository.findAll();
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (Objects.equals(book.getAuthor().getId(), id)) {
                result.add(book);
            }
        }

        return result;

    }
}
