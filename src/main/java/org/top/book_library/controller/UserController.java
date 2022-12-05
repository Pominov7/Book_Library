package org.top.book_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.book_library.db.entity.security.Role;
import org.top.book_library.db.entity.security.User;
import org.top.book_library.service.security.RoleService;
import org.top.book_library.service.security.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping()
    public String showUsersList(Model model) {
        model.addAttribute("users", userService.listAll());
        return "user/users";
    }

    //обработчик на получение формы для обновления юзера
    @GetMapping("/update/{id}")
    public String showUpdateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("action", "update");
        model.addAttribute("user", userService.findById(id));
        List<Role> roles = roleService.listAll();  // список всех ролей
        model.addAttribute("roles", roles);
        return "user/form-user";
    }
    // обработчик для сохранения данных о юзере
    @PostMapping("/save")
    public String saveUser(User user, RedirectAttributes ra,
                           @RequestParam String action) {
        // 1. сохраняем юзера в БД
        User saved = userService.saveUser(user);
        // 2. добавить сообщение о том, что студент сохранен
        ra.addFlashAttribute("message",
                "User " + saved.getUsername() + " " + action + "d successfully");
        // 3. выполнить перенаправление
        return "redirect:/users";
    }

    // обработчик для удаления юзера
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id, RedirectAttributes ra) {
        userService.deleteUserById(id);
        ra.addFlashAttribute("message", "User deleted");
        return "redirect:/users";
    }
}
