package org.top.book_library.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.book_library.controller.filters.AuthorNameFilter;
import org.top.book_library.db.entity.Author;
import org.top.book_library.db.repository.BookRepository;
import org.top.book_library.service.AuthorService;
import org.top.book_library.service.BookService;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    //     Обработчик на вывод списка авторов
    @GetMapping("/page/{pageNo}")
    public String authors(@PathVariable(value = "pageNo") int pageNo, Model m) {
        int pageSize = 9;   // Сколько записей на одной странице
        Page<Author> page = authorService.findPaginated(pageNo, pageSize);
        List<Author> authors = page.getContent().stream()
                .sorted(Comparator.comparing(Author::getLastName))           // сортировка по умолчанию
                .collect(Collectors.toList());
        m.addAttribute("currentPage", pageNo);
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("totalRecords", page.getTotalElements());
        m.addAttribute("authors", authors);
        m.addAttribute("authorNameFilter", authorNameFilter);
        return "/author/authors";

    }

    // Обработчик для фильтрации автора по имени и фамилии
    @PostMapping()
    public String showFilteredAuthors(AuthorNameFilter filter, Model model) {
        List<Author> authors = filter.getFilteredAuthors(authorService);
        model.addAttribute("authors", authors);
        return "/author/authors";
    }

    // Обработчик на получение формы для добавления автора
    @GetMapping("/addAuthor")
    public String addAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "/author/form-author";
    }

    // Обработчик для сохранения автора
    @PostMapping("/addAuthor")
    public String saveAuthor(@ModelAttribute @Valid Author author, BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/author/form-author";
        }
        try {
            authorService.saveAuthor(author);
            redirectAttributes.addFlashAttribute("message",
                    "The author has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/authors/page/1";
    }

    // Обработчик на получение формы для обновления полей автора
    @GetMapping("/edit/{id}")
    public String showFormUpdateToAuthor(@PathVariable("id") Long id, Model model) {
        Author author = authorService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));
        model.addAttribute("author", author);
        return "/author/form-author";
    }

    // Обработчик для обновления полей автора
    @PostMapping("/update")
    public String updateAuthor(@ModelAttribute(value = "author") Author author,
                               RedirectAttributes redirectAttributes) {
        try {
            authorService.updateAuthor(author);
            redirectAttributes.addFlashAttribute("message",
                    "The author has been update successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/authors/page/1";
    }

    // Обработчик для удаления автора
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            bookRepository.clearAuthorInBook(id);
            authorService.deleteAuthorByID(id);
            redirectAttributes.addFlashAttribute("message",
                    "The author has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/authors/page/1";
    }

    // Обработчик для получения списка книг автора
    @GetMapping("/details/{id}")
    public String authorInfo(@PathVariable("id") Long id, Model model) {
        Author author = authorService.getById(id).get();
        model.addAttribute(author);
        model.addAttribute("books", bookService.listBookAuthorId(id));
        return "/author/author-info";

    }
}
