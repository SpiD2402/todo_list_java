package com.example.todo_list.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "priorities")
public class PriorityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priority_id;

    private String name;

    private String color;


}
