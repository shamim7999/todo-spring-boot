package org.dsi.todospringboot.controllers;

import org.dsi.todospringboot.services.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todos")
public class HomeController {
    private final TodoService todoService;

    public HomeController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/add-todo")
    public String processAddTodo() {
        return null;
    }
}
