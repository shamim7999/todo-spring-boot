package org.dsi.todospringboot.repositories;

import org.dsi.todospringboot.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    @Query("SELECT t FROM Todo t WHERE t.isEnabled = true " +
            "AND (:status IS NULL OR :status = '' OR t.status = :status)")
    List<Todo> findAllByIsEnabledTrue(@Param("status") String status);


    @Query("SELECT t FROM Todo t WHERE t.isEnabled = true " +
            "AND (:status IS NULL OR :status = '' OR t.status = :status)")
    Page<Todo> findAllByIsEnabledTrue(@Param("status") String status, Pageable pageable);

    @Query("SELECT t FROM Todo t WHERE t.isEnabled = true")
    Page<Todo> findAllByIsEnabledTrue(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Todo t SET t.isEnabled = false WHERE t.id = :id")
    void softDeleteTodoById(int id);
}
