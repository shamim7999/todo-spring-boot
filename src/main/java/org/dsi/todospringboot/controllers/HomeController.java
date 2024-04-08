package org.dsi.todospringboot.controllers;

import org.dsi.todospringboot.models.Todo;
import org.dsi.todospringboot.services.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todos")
public class HomeController {
    private final TodoService todoService;

    public HomeController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping("/add-todo")
    public String processAddTodo() {
        Todo todo = todoService.save(new Todo(0, "kjsa", "jslkdjalasafas", "4"));
        System.out.println("TODO: "+todo);
        return "index";
    }
}
