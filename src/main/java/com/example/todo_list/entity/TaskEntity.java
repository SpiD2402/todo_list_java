package com.example.todo_list.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "tasks")
@Entity
public class TaskEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private CategoryEntity category;

    private String title;
    private String description;
    private String status;
    private Timestamp create_at;


    @ManyToMany
    @JoinTable(
            name = "tasks_priorities",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "priority_id")
    )
    private List<PriorityEntity> priorities;


    public TaskEntity()
    {
        this.priorities= new ArrayList<>();
    }


}
