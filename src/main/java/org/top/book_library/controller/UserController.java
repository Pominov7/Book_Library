package org.top.book_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.book_library.db.entity.security.Role;
import org.top.book_library.db.entity.security.User;
import org.top.book_library.db.repository.CommentRepository;
import org.top.book_library.service.security.RoleServiceImpl;
import org.top.book_library.service.security.UserServiceIml;

import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    @Autowired
    private UserServiceIml userService;
    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private CommentRepository commentRepository;

    // Обработчик на вывод списка пользователей
    @GetMapping()
    public String showUsersList(Model model) {
        model.addAttribute("users", userService.listAll());
        return "user/users";
    }

    // Обработчик на получение формы для обновления роли пользователя
    @GetMapping("/edit/{id}")
    public String showUpdateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        List<Role> roles = roleService.listAll();  // список всех ролей
        model.addAttribute("roles", roles);
        return "user/form-user";
    }


    // Обработчик для обновления роли пользователя
    @PostMapping("/update")
    public String updateUser(@ModelAttribute(value = "user") User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }


    // Обработчик для удаления пользователя
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes ra) {
        commentRepository.clearUserInComment(id);
        userService.deleteUserById(id);
        ra.addFlashAttribute("message", "User deleted");
        return "redirect:/users";
    }
}
