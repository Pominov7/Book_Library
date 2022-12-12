package org.top.book_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.book_library.controller.filters.BookNameFilter;
import org.top.book_library.db.entity.*;
import org.top.book_library.db.repository.BookRepository;
import org.top.book_library.service.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

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
    @Autowired
    private BookRepository bookRepository;

//    // Обработчик на вывод списка книг
//    @GetMapping()
//    public String books(Model model) {
//        List<Book> books = bookService.listAllBooks();
//        model.addAttribute("books", books);
//        model.addAttribute("bookNameFilter", bookNameFilter);
//
//        return "/book/books";
//    }

    // Обработчик на вывод списка книг
    @GetMapping()
    public String books(Model model, @RequestParam(required = false) String keyword,
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "9") int size,
                        @RequestParam(defaultValue = "title,asc") String[] sort) {
        try {
            List<Book> books;
            String sortField = sort[0];
            String sortDirection = sort[1];

            Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Order order = new Order(direction, sortField);

            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));
            Page<Book> bookPage;
            if (keyword == null) {
                bookPage = bookRepository.findAll(pageable);
            } else {
                bookPage = bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
                model.addAttribute("keyword", keyword);
            }
            books = bookPage.getContent();
            model.addAttribute("books", books);
            model.addAttribute("bookNameFilter", bookNameFilter);
            model.addAttribute("currentPage", bookPage.getNumber() + 1);
            model.addAttribute("totalItems", bookPage.getTotalElements());
            model.addAttribute("totalPages", bookPage.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }


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
    public String saveBook(@ModelAttribute @Valid Book book, BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/book/form-book";
        }
        try {
            bookService.saveBook(book);
            redirectAttributes.addFlashAttribute("message",
                    "The book has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }
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
    public String deleteBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBookByID(id);
            redirectAttributes.addFlashAttribute("message",
                    "The book has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

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
