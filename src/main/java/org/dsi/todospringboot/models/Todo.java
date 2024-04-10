package org.dsi.todospringboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 7, max = 30)
    @Column
    private String title;

    @Size(min = 20, max = 1000)
    @Column
    private String description;

    @Column
    private String priority;

    @Column
    private String status;

    @Column
    private boolean isEnabled;
//    private int priority;
//
//
//    private LocalDateTime createdAt;
//
//
//    private LocalDateTime updatedAt;
}