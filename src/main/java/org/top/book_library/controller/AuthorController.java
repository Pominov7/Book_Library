package org.top.book_library.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.top.book_library.db.entity.Author;
import org.top.book_library.db.entity.Genre;
import org.top.book_library.db.repository.BookRepository;
import org.top.book_library.service.AuthorService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookRepository bookRepository;


    // Обработчик на вывод списка авторов
    @GetMapping()
    public String genres(Model model) {
        List<Author> authors = authorService.listAllAuthors();
        model.addAttribute("authors", authors);
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

    // Обработчик на удаление жанра
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id) {
        bookRepository.clearAuthorInBook(id);
        authorService.deleteAuthorByID(id);
        return "redirect:/authors";
    }
}
