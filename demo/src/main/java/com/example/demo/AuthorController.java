package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorController {

    @GetMapping("/author") // Обрабатывает GET-запросы по пути /author
    public String getAuthorInfo() {
        return "author-info"; // Возвращает имя шаблона (файл author-info.html)
    }
}