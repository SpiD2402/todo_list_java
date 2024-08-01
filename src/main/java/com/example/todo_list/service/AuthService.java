package com.example.todo_list.service;

import com.example.todo_list.dto.AuthSignIn;
import com.example.todo_list.dto.AuthSignUp;
import com.example.todo_list.response.ResponseApi;

public interface AuthService {

    ResponseApi signIn(AuthSignIn authSignIn);
    ResponseApi signUp(AuthSignUp authSignUp) throws Exception;

}
