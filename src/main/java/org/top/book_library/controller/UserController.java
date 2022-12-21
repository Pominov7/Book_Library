package org.top.book_library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("/page/{pageNo}")
    public String users(@PathVariable(value = "pageNo") int pageNo, Model m) {
        int pageSize = 9;   // Сколько записей на одной странице
        Page<User> page = userService.findPaginated(pageNo, pageSize);
        List<User> users = page.getContent().stream()
                .sorted(Comparator.comparing(User::getUsername))           // сортировка по умолчанию
                .collect(Collectors.toList());
        m.addAttribute("currentPage", pageNo);
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("totalRecords", page.getTotalElements());
        m.addAttribute("users", users);
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
    public String updateUser(@ModelAttribute(value = "user") User user,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message",
                    "The user has been update successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users/page/1";
    }


    // Обработчик для удаления пользователя
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            commentRepository.clearUserInComment(id);
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("message",
                    "The user with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users/page/1";
    }
}
