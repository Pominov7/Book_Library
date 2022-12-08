package org.top.book_library.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.top.book_library.controller.filters.AuthorNameFilter;
import org.top.book_library.db.entity.Author;
import org.top.book_library.db.repository.BookRepository;
import org.top.book_library.service.AuthorService;
import org.top.book_library.service.BookService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorNameFilter authorNameFilter;

    @Autowired
    private BookService bookService;



    // Обработчик на вывод списка авторов
    @GetMapping()
    public String authors(Model model) {
        List<Author> authors = authorService.listAllAuthors();
        model.addAttribute("authors", authors);
        model.addAttribute("authorNameFilter", authorNameFilter);
        return "/author/authors";
    }

    // Фильтр автора по имени и фамилии
    @PostMapping()
    public String showFilteredAuthors(AuthorNameFilter filter, Model model) {
        List<Author> authors = filter.getFilteredAuthors(authorService);
        model.addAttribute("authors", authors);
        model.addAttribute("containsFilter", filter);
        return "/author/authors";
    }

    // Обработчик на добавление автора
    @GetMapping("/addAuthor")
    public String addAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "/author/form-author";
    }

    // Обработчик на сохранение автора
    @PostMapping("/addAuthor")
    public String saveAuthor(@ModelAttribute @Valid Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "/author/form-author";
        }
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }


    // UPDATE (редактирование полей автора)
    @GetMapping("/edit/{id}")
    public String showFormUpdateToAuthor(@PathVariable("id") Long id, Model model) {
        Author author = authorService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));
        model.addAttribute("author", author);
        return "/author/form-author";
    }

    // Обработчик для обновления автора
    @PostMapping("/update")
    public String updateAuthor(@ModelAttribute(value = "author") Author author) {
        authorService.updateAuthor(author);
        return "redirect:/covers";
    }

    // Обработчик на удаление автора
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id) {
        bookRepository.clearAuthorInBook(id);
        authorService.deleteAuthorByID(id);
        return "redirect:/authors";
    }

    // просмотр книг автора
    @GetMapping("/details/{id}")
    public String authorInfo(@PathVariable("id") Long id, Model model) {
        Author author = authorService.getById(id).get();
        model.addAttribute(author);
        model.addAttribute("books", bookService.listBookAuthorId(id));
        return "/author/author-info";

    }
}
