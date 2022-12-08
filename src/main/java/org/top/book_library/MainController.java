package org.top.book_library;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Главный контроллер
@Controller
public class MainController {

    // обработчик для вывода главной страницы
    @GetMapping("")
    public String showHomePage() {
        // возвращает представление index
        return "index"; // index.html шаблон
    }
}