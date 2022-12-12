package org.top.book_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.book_library.controller.filters.GenreNameFilter;
import org.top.book_library.db.entity.Genre;
import org.top.book_library.db.repository.BookRepository;
import org.top.book_library.service.BookService;
import org.top.book_library.service.GenreService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private GenreNameFilter genreNameFilter;

    // Обработчик на вывод списка жанров
    @GetMapping()
    public String genres(Model model) {
        List<Genre> genres = genreService.listAllGenres();
        model.addAttribute("genres", genres);
        model.addAttribute("genreNameFilter", genreNameFilter);
        return "/genre/genres";
    }

    // Обработчик для фильтрации жанра по названию
    @PostMapping()
    public String showFilteredGenres(GenreNameFilter filter, Model model) {
        List<Genre> genres = filter.getFilteredGenres(genreService);
        model.addAttribute("genres", genres);
        model.addAttribute("containsFilter", filter);
        return "/genre/genres";
    }


    // Обработчик на получение формы для добавления жанра
    @GetMapping("/addGenre")
    public String addGenre(Model model) {
        model.addAttribute("genre", new Genre());
        return "/genre/form-genre";
    }

    // Обработчик для сохранения жанра
    @PostMapping("/addGenre")
    public String saveGenre(@ModelAttribute @Valid Genre genre, BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/genre/form-genre";
        }
        try {
            genreService.saveGenre(genre);
            redirectAttributes.addFlashAttribute("message",
                    "The genre has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/genres";
    }

    // Обработчик на получение формы для обновления полей жанра
    @GetMapping("/edit/{id}")
    public String showFormUpdateToGenre(@PathVariable("id") Long id, Model model) {
        Genre genre = genreService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        model.addAttribute("genre", genre);
        return "/genre/form-genre";
    }

    // Обработчик для обновления полей жанра
    @PostMapping("/update")
    public String updateGenre(@ModelAttribute(value = "genre") Genre genre) {
        genreService.updateGenre(genre);
        return "redirect:/genres";
    }

    // Обработчик для удаления жанра
    @GetMapping("/delete/{id}")
    public String deleteGenre(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            bookRepository.clearGenreInBook(id);
            genreService.deleteGenreByID(id);
            redirectAttributes.addFlashAttribute("message",
                    "The genre has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/genres";
    }


    // Обработчик для получения списка книг в выбранном жанре
    @GetMapping("/details/{id}")
    public String genreInfo(@PathVariable("id") Long id, Model model) {
        Genre genre = genreService.getById(id).get();
        model.addAttribute(genre);
        model.addAttribute("books", bookService.listBookGenreId(id));
        return "/genre/genre-info";

    }
}
