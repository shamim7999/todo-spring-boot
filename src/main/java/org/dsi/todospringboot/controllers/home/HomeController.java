package org.dsi.todospringboot.controllers.home;

import org.dsi.todospringboot.models.Todo;
import org.dsi.todospringboot.services.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {
    private final TodoService todoService;

    public HomeController(TodoService todoService) {
        this.todoService = todoService;
    }

    @ModelAttribute
    public Todo sendDummyTodo() {
        return new Todo();
    }

    @GetMapping({"/", "/index"})
    public String sendIndex() {
        return "index";
    }

    @GetMapping("/show-todos")
    public String showTodos(Model model) {
        List<Todo> todos = todoService.findAll();
        model.addAttribute("todos", todos);
        return "show-todos";
    }

    @GetMapping("/soft-delete-todo")
    public String softDeleteTodoById(@RequestParam int id) {
        todoService.softDeleteTodo(id);
        return "redirect:/show-todos";
    }

    @PostMapping("/add-todo")
    public String processAddTodo(@ModelAttribute Todo todo) {
        todoService.save(todo);
        return "index";
    }
}
