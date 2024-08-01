package com.example.todo_list.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "categories")
@Table
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;
    private String name;
    private boolean status;

}
