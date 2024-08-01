package com.example.todo_list.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class TaskDto {


    @NotNull
    private Long category_id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;




}
