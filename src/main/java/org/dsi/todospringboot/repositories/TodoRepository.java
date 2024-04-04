package org.dsi.todospringboot.repositories;

import org.dsi.todospringboot.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
