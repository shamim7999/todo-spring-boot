package org.dsi.todospringboot.controllers.home;

import jakarta.validation.Valid;
import org.dsi.todospringboot.helper.Message;
import org.dsi.todospringboot.models.Todo;
import org.dsi.todospringboot.services.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showTodos(@RequestParam(required = false) Optional<String> status,
                            @RequestParam("page") Optional<Integer> todoPage,
                            Model model) {

        int currentTodoPage = todoPage.orElse(1);
        String myStatus = status.orElse(null);

        Map<String, Page<?>> pageResult = todoService.findAll(myStatus, currentTodoPage, 3);

        model.addAttribute("todos", pageResult.get("todos"));
        model.addAttribute("formattedDate", pageResult.get("formattedDate"));
        model.addAttribute("currentTodoPage", currentTodoPage);
        model.addAttribute("totalTodoPages", pageResult.get("todos").getTotalPages());
        model.addAttribute("status", myStatus);

        return "show-todos";
    }

    @GetMapping("/soft-delete-todo")
    public String softDeleteTodoById(@RequestParam int id,
                                     RedirectAttributes redirectAttributes) {
        todoService.softDeleteTodo(id);
        redirectAttributes.addFlashAttribute("msg",
                new Message("Todo deleted successfully", "alert-success"));
        return "redirect:/show-todos";
    }

    @PostMapping("/update-todo")
    public String processUpdateTodo(@Valid @ModelAttribute Todo todo,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            System.out.println(result);
            redirectAttributes.addFlashAttribute("msg",
                    new Message("Todo could not updated", "alert-danger"));
            return "redirect:/show-todos";
        }
        todoService.save(todo);
        redirectAttributes.addFlashAttribute("msg",
                new Message("Todo updated successfully", "alert-success"));
        return "redirect:/show-todos";
    }
    @PostMapping("/add-todo")
    public String processAddTodo(@Valid @ModelAttribute Todo todo,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            System.out.println(result);
            redirectAttributes.addFlashAttribute("msg",
                    new Message("Todo could not added", "alert-danger"));
            return "redirect:/show-todos";
        }
        todoService.save(todo);
        redirectAttributes.addFlashAttribute("msg",
                new Message("Todo Added successfully", "alert-success"));
        return "redirect:/show-todos";
    }

    @GetMapping("/mark-as-completed")
    public String markAsCompleted(@RequestParam int id, RedirectAttributes redirectAttributes) {
        Todo todo = todoService.findTodoById(id);
        todo.setStatus("Completed");
        todoService.save(todo);
        redirectAttributes.addFlashAttribute("msg",
                new Message("Todo Marked as Completed", "alert-success"));
        return "redirect:/show-todos";
    }
}
