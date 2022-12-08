package org.top.book_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.top.book_library.controller.filters.BookNameFilter;
import org.top.book_library.db.entity.*;
import org.top.book_library.service.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CoverService coverService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private BookNameFilter bookNameFilter;


    // Обработчик на вывод списка книг
    @GetMapping()
    public String books(Model model) {
        List<Book> books = bookService.listAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("bookNameFilter", bookNameFilter);

        return "/book/books";
    }


    // Обработчик для фильтрации книг
    @PostMapping()
    public String showFilteredBooks(BookNameFilter filter, Model model) {
        List<Book> books = filter.getFilteredBooks(bookService);
        model.addAttribute("books", books);
        model.addAttribute("containsFilter", filter);
        return "/book/books";

    }

    // Обработчик на получение формы для добавления книги
    @GetMapping("/addBook")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        List<Genre> genres = genreService.listAllGenres();
        model.addAttribute("genresList", genres);
        List<Link> links = linkService.listAllLinks();
        model.addAttribute("linksList", links);
        List<Author> authors = authorService.listAllAuthors();
        model.addAttribute("authorsList", authors);
        List<Cover> covers = coverService.listAllCovers();
        model.addAttribute("coversList", covers);
        return "/book/form-book";
    }

    // Обработчик для сохранения книги
    @PostMapping("/addBook")
    public String saveBook(@ModelAttribute @Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "/book/form-book";
        }
        bookService.saveBook(book);
        return "redirect:/books";
    }

    // Обработчик на получение формы для обновления полей книги
    @GetMapping("/edit/{id}")
    public String showFormUpdateToBook(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        List<Genre> genres = genreService.listAllGenres();
        model.addAttribute("genresList", genres);
        List<Link> links = linkService.listAllLinks();
        model.addAttribute("linksList", links);
        List<Author> authors = authorService.listAllAuthors();
        model.addAttribute("authorsList", authors);
        List<Cover> covers = coverService.listAllCovers();
        model.addAttribute("coversList", covers);
        return "/book/form-book";
    }

    // Обработчик для обновления полей книги
    @PostMapping("/update")
    public String updateBook(@ModelAttribute(value = "book") Book book) {
        bookService.updateBook(book);
        return "redirect:/books";
    }

    // Обработчик для удаления книги
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBookByID(id);
        return "redirect:/books";
    }

    // Обработчик для получения полной информации о книге
    @GetMapping("/details/{id}")
    public String bookInfo(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getById(id).get();
        model.addAttribute(book);
        List<Comment> comments = commentService.findAllComments(book);
        model.addAttribute("comments", comments);
        return "/book/book-info";

    }

}
