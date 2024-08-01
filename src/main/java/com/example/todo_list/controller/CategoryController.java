package com.example.todo_list.controller;

import com.example.todo_list.dto.CategoryDto;
import com.example.todo_list.dto.RoleDto;
import com.example.todo_list.message.Const;
import com.example.todo_list.response.ResponseApi;
import com.example.todo_list.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService ;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseApi all()
    {
        return   categoryService.allCategory();
    }


    @GetMapping("/{id}")
    public ResponseApi getById(@PathVariable Long id)
    {
        return   categoryService.getByIdCategory(id);
    }



    @PostMapping
    public ResponseApi add(@Valid @RequestBody CategoryDto categoryDto , BindingResult result)
    {
        if (result.hasErrors())
        {
            return  new ResponseApi(Const.STATUS_BAD_REQUEST,Const.MSG_BAD_REQUEST, Optional.of(validar(result).getBody()));
        }
        return categoryService.addCategory(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseApi delete(@Valid Long id)
    {
        return categoryService.deleteCategory(id);
    }


    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String>errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo "+ err.getField()+" "+err.getDefaultMessage());});
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

}
