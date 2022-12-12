package org.top.book_library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.top.book_library.db.entity.Book;
import org.top.book_library.db.repository.BookRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    // получить книгу по id
    @Override
    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }


    // получить список всех книг
    @Override
    public List<Book> listAllBooks() {
        return bookRepository.findAll().stream()
                .sorted(Comparator.comparing(Book::getTitle))           // сортировка по умолчанию
                .collect(Collectors.toList());
    }

    // сохранить книгу
    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    // редактировать поля книги
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

    // получить книгу по содержимому строки
    @Override
    public List<Book> findByContains(String match) {
        if (match == null || match.equals(""))
            return bookRepository.findAll();
        return bookRepository.findAll()
                .stream()
                .filter(s -> s.getTitle().toLowerCase().contains(match.toLowerCase())
                        || s.getAuthor().toString().toLowerCase().contains(match.toLowerCase())
                        || s.getGenre().getName().toLowerCase().contains(match.toLowerCase()))  //игнорируем регистр
                .toList();
    }

    // получить список книг определенного автора
    @Override
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

    // получить список книг определенного жанра
    @Override
    public List<Book> listBookGenreId(Long id) {
        List<Book> books = bookRepository.findAll();
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (Objects.equals(book.getGenre().getId(), id)) {
                result.add(book);
            }
        }

        return result;

    }

    @Override
    public Page<Book> findPaginated(int pageNo, int size) {
        Pageable pageable = PageRequest.of(pageNo-1,size);
        return bookRepository.findAll(pageable);
    }
}
