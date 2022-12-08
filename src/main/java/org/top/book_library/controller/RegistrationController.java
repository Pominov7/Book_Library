package org.top.book_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.top.book_library.db.entity.security.User;
import org.top.book_library.service.security.UserServiceIml;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private UserServiceIml userService;

    // Обработчик для получения формы регистрации пользователя
    @GetMapping("/registration")
    public String registration() {
        return "layout/registration";
    }

    // Обработчик для добавления зарегистрированного пользователя
    @PostMapping("/registration")
    public String addUser(@Valid User user, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "/index";
        }
        User userFromDb = userService.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("message", "User " + user.getUsername() + " exists!");
            return "layout/registration";
        }

        userService.addUser(user);

        return "redirect:/login";
    }
}
