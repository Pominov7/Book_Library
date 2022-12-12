package org.top.book_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.book_library.db.entity.Link;
import org.top.book_library.db.repository.BookRepository;
import org.top.book_library.service.LinkService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/links")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private BookRepository bookRepository;

    // Обработчик на вывод списка всех ссылок
    @GetMapping()
    public String links(Model model) {
        List<Link> links = linkService.listAllLinks();
        model.addAttribute("links", links);
        return "/link/links";
    }

    // Обработчик на получение формы для добавления ссылки
    @GetMapping("/addLink")
    public String addLink(Model model) {
        model.addAttribute("link", new Link());
        return "/link/form-link";
    }

    // Обработчик для сохранения ссылки
    @PostMapping("/addLink")
    public String saveLink(@ModelAttribute @Valid Link link, BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/link/form-link";
        }
        try {
            linkService.saveLink(link);
            redirectAttributes.addFlashAttribute("message",
                    "The link has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/links";
    }

    // Обработчик на получение формы для обновления полей ссылки
    @GetMapping("/edit/{id}")
    public String showFormUpdateToLink(@PathVariable("id") Long id, Model model) {
        Link link = linkService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid link Id:" + id));
        model.addAttribute("link", link);
        return "/link/form-link";
    }

    // Обработчик для обновления полей ссылки
    @PostMapping("/update")
    public String updateLink(@ModelAttribute(value = "link") Link link) {
        linkService.updateLink(link);
        return "redirect:/covers";
    }

    // Обработчик для удаления ссылки
    @GetMapping("/delete/{id}")
    public String deleteLink(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            bookRepository.clearLinkInBook(id);
            linkService.deleteLinkByID(id);
            redirectAttributes.addFlashAttribute("message",
                    "The link has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/links";
    }

}
