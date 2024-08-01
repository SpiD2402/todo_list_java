package com.example.todo_list.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthSignIn {

    @NotBlank
    private String username;
    @NotBlank
    private String password;


}
