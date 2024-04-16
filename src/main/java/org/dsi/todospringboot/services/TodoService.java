package org.dsi.todospringboot.services;

import org.dsi.todospringboot.helper.Shorter;
import org.dsi.todospringboot.helper.TimestampConverter;
import org.dsi.todospringboot.models.Todo;
import org.dsi.todospringboot.repositories.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public void save(Todo todo) {
        todo.setEnabled(true);
        todo.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
        todoRepository.save(todo);
    }

    @Transactional
    public void softDeleteTodo(int id) {
        todoRepository.softDeleteTodoById(id);
    }

    public Map<String, List<?>> findAll(String status) {
        List<Todo> todos = todoRepository
                .findAllByIsEnabledTrue(status)
                .stream()
                .map((todo) -> {
                    todo.setTitle(Shorter.makeShortTheSentence(todo.getTitle(), 10));
                    todo.setDescription(Shorter.makeShortTheSentence(todo.getDescription(), 30));
                    return todo;
                })
                .collect(Collectors.toList());

        List<String> formattedDate = todos
                .stream()
                .map((todo) -> TimestampConverter.convertTimestampToString(todo.getUpdatedTime()))
                .collect(Collectors.toList());

        Map<String, List<?>> result = new HashMap<>();
        result.put("todos", todos);
        result.put("formattedDate", formattedDate);

        return result;
    }

    public Map<String, Integer> findAllkindsOfTodos() {
        List<Todo> todos = todoRepository.findAllByIsEnabledTrue();
        Map<String, Integer> result = new HashMap<>();

        int pendingCount = (int) todos.stream()
                .filter(todo -> "Pending".equals(todo.getStatus()))
                .count();

        int completedCount = (int) todos.stream()
                .filter(todo -> "Completed".equals(todo.getStatus()))
                .count();

        int inProgressCount = (int) todos.stream()
                .filter(todo -> "In Progress".equals(todo.getStatus()))
                .count();

        result.put("pending", pendingCount);
        result.put("completed", completedCount);
        result.put("inProgress", inProgressCount);

        return result;
    }

    public Map<String, Page<?>> findAll(String status, int currentPage, int size) {
        Pageable pageable = PageRequest.of(currentPage-1, size);

        Page<Todo> todoPage = todoRepository.findAllByIsEnabledTrue(status, pageable);
        Page<String> formattedDatePage = todoPage
                .map(todo -> TimestampConverter.convertTimestampToString(todo.getUpdatedTime()));

        Map<String, Page<?>> result = new HashMap<>();
        result.put("todos", todoPage);
        result.put("formattedDate", formattedDatePage);

        return result;
    }


    public Map<String, Page<?>> findAllBySearch(String query, int currentPage, int size) {
        Pageable pageable = PageRequest.of(currentPage-1, size);

        Page<Todo> todoPage = todoRepository.findByTitleContainingOrDescriptionContainingAndIsEnabledIsTrue(query,query, pageable);
        Page<String> formattedDatePage = todoPage
                .map(todo -> TimestampConverter.convertTimestampToString(todo.getUpdatedTime()));

        Map<String, Page<?>> result = new HashMap<>();
        result.put("todos", todoPage);
        result.put("formattedDate", formattedDatePage);

        return result;
    }

    public Todo findTodoById(int id) {
        return todoRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Resource not found for ID: " + id)
                );
    }

}
