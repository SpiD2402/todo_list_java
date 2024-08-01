package com.example.todo_list.controller;


import com.example.todo_list.dto.AuthSignIn;
import com.example.todo_list.dto.AuthSignUp;
import com.example.todo_list.message.Const;
import com.example.todo_list.response.ResponseApi;
import com.example.todo_list.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signin")
    public ResponseApi authSignIn(@Valid  @RequestBody AuthSignIn authSignIn, BindingResult result)
    {
        if(result.hasErrors())
        {
            return  new ResponseApi(Const.STATUS_BAD_REQUEST,Const.MSG_BAD_REQUEST, Optional.of(validar(result).getBody()));
        }
        try{
            return  authService.signIn(authSignIn);
        }
        catch (ExpiredJwtException e)
        {
            return  new ResponseApi(Const.STATUS_UNAUTHORIZED, e.getMessage(),Optional.empty());
        }

    }

    @PostMapping("/signup")
    public ResponseApi authSignUp(@Valid  @RequestBody AuthSignUp authSignUp, BindingResult result) throws Exception {
        if(result.hasErrors())
        {
            return  new ResponseApi(Const.STATUS_BAD_REQUEST,Const.MSG_BAD_REQUEST, Optional.of(validar(result).getBody()));
        }

        return  authService.signUp(authSignUp);
    }


    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String>errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo "+ err.getField()+" "+err.getDefaultMessage());});
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

}
