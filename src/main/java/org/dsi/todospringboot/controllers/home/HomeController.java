package org.dsi.todospringboot.controllers.home;

import org.dsi.todospringboot.models.Todo;
import org.dsi.todospringboot.services.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public String showTodos(@RequestParam(required = false) String status, Model model) {
        Map<String, List<?> > result = todoService.findAll(status);
        model.addAttribute("todos", result.get("todos"));
        model.addAttribute("formattedDate", result.get("formattedDate"));
        return "show-todos";
    }

    @GetMapping("/soft-delete-todo")
    public String softDeleteTodoById(@RequestParam int id) {
        todoService.softDeleteTodo(id);
        return "redirect:/show-todos";
    }

    @PostMapping("/update-todo")
    public String processUpdateTodo(@ModelAttribute Todo todo) {
        todoService.save(todo);
        return "redirect:/show-todos";
    }
    @PostMapping("/add-todo")
    public String processAddTodo(@ModelAttribute Todo todo) {
        todoService.save(todo);
        return "index";
    }

    @GetMapping("/mark-as-completed")
    public String markAsCompleted(@RequestParam int id) {
        Todo todo = todoService.findTodoById(id);
        todo.setStatus("Completed");
        todoService.save(todo);
        return "redirect:/show-todos";
    }
}
