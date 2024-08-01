package com.example.todo_list.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleDto {

    @NotBlank
    private String name;
}
