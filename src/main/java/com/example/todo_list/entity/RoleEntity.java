package com.example.todo_list.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "roles")
@Entity
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;
    private String name;
    private boolean status;

}
