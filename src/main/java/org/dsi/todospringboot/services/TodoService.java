package org.dsi.todospringboot.services;

import org.dsi.todospringboot.helper.Shorter;
import org.dsi.todospringboot.models.Todo;
import org.dsi.todospringboot.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void save(Todo todo) {
        todo.setEnabled(true);
        todo.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
        System.out.println(
                todo.getId() + " " +
                        todo.getDescription() + " " +
                        todo.getTitle() + " " +
                        todo.getStatus() + " " +
                        todo.getPriority() + " " +
                        todo.getUpdatedTime()
        );
        todoRepository.save(todo);
    }

    public void softDeleteTodo(int id) {
        todoRepository.softDeleteTodoById(id);
    }
    public List <Todo> findAll() {

        return todoRepository
                .findAllByIsEnabledTrue()
                .stream()
                .map((todo) -> {
                    todo.setTitle(Shorter.makeShortTheSentence(todo.getTitle(), 10));
                    todo.setDescription(Shorter.makeShortTheSentence(todo.getDescription(), 30));
                    return todo;
                })
                .collect(Collectors.toList());
    }

    public Todo findTodoById(int id) {
        return todoRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Resource not found for ID: " + id)
                );
    }

}
