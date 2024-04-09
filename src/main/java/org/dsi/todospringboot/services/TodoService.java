package org.dsi.todospringboot.services;

import org.dsi.todospringboot.models.Todo;
import org.dsi.todospringboot.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void save(Todo todo) {
        todo.setEnabled(true);
        todoRepository.save(todo);
    }

    public void softDeleteTodo(int id) {
        todoRepository.softDeleteTodoById(id);
    }
    public List <Todo> findAll() {
        return todoRepository.findAllByIsEnabledTrue();
    }
}
