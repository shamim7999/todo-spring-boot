package org.dsi.todospringboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 7, max = 30, message = "Title should have 7 to 30 characters.")
    @Column
    private String title;

    @Size(min = 20, max = 1000,  message = "Description should have 20 to 1000 characters.")
    @Column
    private String description;

    @Column
    private String priority;

    @Column
    private String status;

    @Column
    private boolean isEnabled;

    @Column
    private Timestamp updatedTime;
}