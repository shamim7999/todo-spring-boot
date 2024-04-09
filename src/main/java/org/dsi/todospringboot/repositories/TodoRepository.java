package org.dsi.todospringboot.repositories;

import org.dsi.todospringboot.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    @Query("SELECT t FROM Todo t WHERE t.isEnabled = true")
    List<Todo> findAllByIsEnabledTrue();

    @Modifying
    @Transactional(readOnly = true)
    @Query("UPDATE Todo t SET t.isEnabled = false WHERE t.id = :id")
    void softDeleteTodoById(int id);
}
