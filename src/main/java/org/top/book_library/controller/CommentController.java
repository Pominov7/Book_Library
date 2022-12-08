package org.top.book_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.top.book_library.db.entity.Book;
import org.top.book_library.db.entity.Comment;
import org.top.book_library.service.CommentService;
import org.top.book_library.service.security.UserServiceIml;

import java.security.Principal;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserServiceIml userService;

    // Обработчик на получение формы для добавления комментария
    @GetMapping(value = "/addComment", params = "bookId")
    public String addComment(@RequestParam("bookId") Book book, Model model, Principal principal) {
        model.addAttribute("comment", new Comment("", book
                , userService.findByUsername((principal.getName()))));

        return "/comment/form-comment";
    }

    // Обработчик для сохранения комментария
    @PostMapping("/addComment")
    public String saveComment(@ModelAttribute Comment comment) {
        commentService.addOrSaveComment(comment);
        long id = comment.getBook().getId();
        return "redirect:/books/details/" + id;
    }

    // Обработчик на получение формы для обновления полей комментария
    // Не используется
    @GetMapping("/edit/{comment}")
    public String editComment(@PathVariable Comment comment, Model model) {
        model.addAttribute("comment", comment);
        return "/comment/form-comment";
    }

    // Обработчик для удаления комментария
    @PostMapping("/delete/{comment}")
    public String deleteComment(@PathVariable Comment comment) {
        commentService.deleteComment(comment);
        long id = comment.getBook().getId();
        return "redirect:/books/details/" + id;
    }

}
