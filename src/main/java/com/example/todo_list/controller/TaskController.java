package com.example.todo_list.controller;

import com.example.todo_list.dto.TaskDto;
import com.example.todo_list.message.Const;
import com.example.todo_list.response.ResponseApi;
import com.example.todo_list.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseApi all()
    {
        return taskService.allTask();
    }

    @GetMapping("/{id}")
    public ResponseApi getById(@PathVariable Long id)
    {
        return  taskService.getBYIdTask(id);

    }


    @PostMapping
    public  ResponseApi add(@Valid @RequestBody TaskDto taskDto, BindingResult result)
    {
        if (result.hasErrors())
        {
            return  new ResponseApi(Const.STATUS_BAD_REQUEST,Const.MSG_BAD_REQUEST, Optional.of(validar(result).getBody()));

        }
        return taskService.addTask(taskDto);


    }

    @DeleteMapping("/{id}")
    public ResponseApi delete(@PathVariable Long id)
    {
        return  taskService.deleteTask(id);
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String>errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo "+ err.getField()+" "+err.getDefaultMessage());});
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

}
