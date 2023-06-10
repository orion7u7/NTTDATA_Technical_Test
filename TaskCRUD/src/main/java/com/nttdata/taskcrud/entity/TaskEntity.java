package com.nttdata.taskcrud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class TaskEntity {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task")
    private Long id;

    @Column(name = "title_task", nullable = false, length = 50)
    @NotEmpty(message = "Title is required")
    private String title;

    @Column(name = "description_task", length = 100)
    private String description;

    @Column(name = "start_date_task")
    private Date startDate;

    @Column(name = "end_date_task")
    private Date endDate;

    @Column(name = "id_user", nullable = false, length = 50)
    @Positive(message = "User ID must be positive")
    private int idUser;
}
