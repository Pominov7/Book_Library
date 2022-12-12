package org.top.book_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.book_library.db.entity.*;
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

    // Обработчик на вывод списка обложек
    @GetMapping()
    public String covers(Model model) {
        List<Cover> covers = coverService.listAllCovers();
        model.addAttribute("covers", covers);
        return "/cover/covers";
    }

    // Обработчик на получение формы для добавления обложки
    @GetMapping("/addCover")
    public String addCover(Model model) {
        model.addAttribute("cover", new Cover());
        return "/cover/form-cover";
    }

    // Обработчик для сохранения обложки
    @PostMapping("/addCover")
    public String saveCover(@ModelAttribute @Valid Cover cover, BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/cover/form-cover";
        }
        try {
            coverService.saveCover(cover);
            redirectAttributes.addFlashAttribute("message",
                    "The cover has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/covers";
    }


    // Обработчик на получение формы для обновления полей обложки
    @GetMapping("/edit/{id}")
    public String showUpdateFormCover(@PathVariable("id") Long id, Model model) {
        Cover cover = coverService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cover Id:" + id));
        model.addAttribute("cover", cover);
        return "/cover/form-cover";
    }

    // Обработчик для обновления полей обложки
    @PostMapping("/update")
    public String updateCover(@ModelAttribute(value = "cover") Cover cover) {
        coverService.updateCover(cover);
        return "redirect:/covers";
    }

    // Обработчик для удаления обложки
    @GetMapping("/delete/{id}")
    public String deleteCover(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            bookRepository.clearCoverInBook(id);
            coverService.deleteCoverByID(id);
            redirectAttributes.addFlashAttribute("message",
                    "The cover has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/covers";
    }

}
