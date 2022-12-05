package org.top.book_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.top.book_library.db.entity.Cover;
import org.top.book_library.db.repository.BookRepository;
import org.top.book_library.service.CoverService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/covers")
public class CoverController {
    @Autowired
    private CoverService coverService;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping()
    public String covers(Model model) {
        List<Cover> covers = coverService.listAllCovers();
        model.addAttribute("covers", covers);
        return "/cover/covers";
    }

    // Обработчик на добавление обложки
    @GetMapping("/addCover")
    public String addCover(Model model) {
        model.addAttribute("cover", new Cover());
        return "/cover/form-cover";
    }

    // Обработчик на сохранение обложки
    @PostMapping("/addCover")
    public String saveCover(@ModelAttribute @Valid Cover cover, BindingResult result) {
        if (result.hasErrors()) {
            return "/cover/form-cover";
        }
        coverService.saveCover(cover);
        return "redirect:/covers";
    }

    // Обработчик на удаления обложки
    @GetMapping("/delete/{id}")
    public String deleteCover(@PathVariable("id") Long id) {
        bookRepository.clearCoverInBook(id);
        coverService.deleteCoverByID(id);
        return "redirect:/covers";
    }

}
