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

import java.util.HashMap;
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
    public String sendIndex(Model model) {
        Map<String, Integer> result = todoService.findAllkindsOfTodos();

        model.addAttribute("pending", result.get("pending"));
        model.addAttribute("completed", result.get("completed"));
        model.addAttribute("inProgress", result.get("inProgress"));

        return "index";
    }

    //@ResponseBody
    @GetMapping("/show-todos/queries")
    public String showTodosBySearch(@RequestParam("page") Optional<Integer> todoPage,
                                    @RequestParam(value = "query", required = false) Optional<String> query,
                                    Model model) {
        int currentTodoPage = todoPage.orElse(1);
        String myQuery = query.orElse("").trim();

        System.out.println("I am in query: "+query);

        Map<String, Page<?>> pageResult = todoService.findAllBySearch(myQuery, currentTodoPage, 3);

        model.addAttribute("todos", pageResult.get("todos"));
        model.addAttribute("formattedDate", pageResult.get("formattedDate"));
        model.addAttribute("currentTodoPage", currentTodoPage);
        model.addAttribute("totalTodoPages", pageResult.get("todos").getTotalPages());
        model.addAttribute("myQuery", myQuery);
        model.addAttribute("showQuery", true);

        return "show-todos";
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
        model.addAttribute("showQuery", false);

        return "show-todos";
    }

    @GetMapping("/soft-delete-todo")
    public String softDeleteTodoById(@RequestParam int id,
                                     RedirectAttributes redirectAttributes) {
        todoService.softDeleteTodo(id);
        redirectAttributes.addFlashAttribute("msg",
                new Message("Todo deleted successfully", "alert-success", null));
        return "redirect:/show-todos";
    }

    @PostMapping("/update-todo")
    public String processUpdateTodo(@Valid @ModelAttribute Todo todo,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            System.out.println(result);
            redirectAttributes.addFlashAttribute("msg",
                    new Message("Todo could not updated", "alert-danger", Message.errorMessage(result)));
            return "redirect:/show-todos";
        }
        todoService.save(todo);
        redirectAttributes.addFlashAttribute("msg",
                new Message("Todo updated successfully", "alert-success", null));
        return "redirect:/show-todos";
    }
    @PostMapping("/add-todo")
    public String processAddTodo(@Valid @ModelAttribute Todo todo,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            System.out.println(result);
            redirectAttributes.addFlashAttribute("msg",
                    new Message("Todo could not added", "alert-danger", Message.errorMessage(result)));
            return "redirect:/show-todos";
        }
        todoService.save(todo);
        redirectAttributes.addFlashAttribute("msg",
                new Message("Todo Added successfully", "alert-success", null));
        return "redirect:/show-todos";
    }

    @GetMapping("/mark-as-completed")
    public String markAsCompleted(@RequestParam int id, RedirectAttributes redirectAttributes) {
        Todo todo = todoService.findTodoById(id);
        todo.setStatus("Completed");
        todoService.save(todo);
        redirectAttributes.addFlashAttribute("msg",
                new Message("Todo Marked as Completed", "alert-success", null));
        return "redirect:/show-todos";
    }
}
