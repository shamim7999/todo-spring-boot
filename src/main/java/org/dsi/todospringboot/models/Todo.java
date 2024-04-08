package org.dsi.todospringboot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    private String title;


    @Column
    private String description;

    @Column
    private String ratings;

//    private int priority;
//
//
//    private LocalDateTime createdAt;
//
//
//    private LocalDateTime updatedAt;
}