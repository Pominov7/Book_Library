package org.top.book_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.book_library.db.entity.security.User;
import org.top.book_library.service.security.UserServiceIml;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private UserServiceIml userService;

    // Обработчик для получения формы регистрации пользователя
    @GetMapping("/registration")
    public String registration(User user, Model model) {
        model.addAttribute("user", user);
        return "layout/registration";
    }

    // Обработчик для добавления зарегистрированного пользователя
    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult result,
                          RedirectAttributes redirectAttributes) {
        try {
            if (userService.findByUsername(user.getUsername()) != null) {
                result.addError(new FieldError("user", "username", "User "
                        + user.getUsername() + " exists!"));
            }
            if (userService.findByUsername(user.getPassword()) != null) {
                result.addError(new FieldError("user", "password", "Password "
                        + user.getUsername() + " exists!"));
            }

            if (result.hasErrors()) {
                return "layout/registration";
            }
            userService.addUser(user);
            redirectAttributes.addFlashAttribute("message",
                    "Registration was successful!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }
        return "redirect:/login";
    }
}
