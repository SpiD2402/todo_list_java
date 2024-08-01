package com.example.todo_list.controller;

import com.example.todo_list.dto.UserDto;
import com.example.todo_list.message.Const;
import com.example.todo_list.response.ResponseApi;
import com.example.todo_list.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseApi all()
    {
        return userService.allUser();

    }

    @GetMapping("/load")
    private ResponseApi loadByUser()
    {
        return  userService.loadByUser();
    }

    @PostMapping()
    private ResponseApi add (@Valid @RequestBody UserDto userDto, BindingResult result) throws Exception {
        if (result.hasErrors())
        {
            return  new ResponseApi(Const.STATUS_BAD_REQUEST,Const.MSG_BAD_REQUEST, Optional.of(validar(result).getBody()));

        }
        return  userService.addUser(userDto);

    }
    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String>errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo "+ err.getField()+" "+err.getDefaultMessage());});
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
