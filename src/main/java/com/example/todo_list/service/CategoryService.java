package com.example.todo_list.service;

import com.example.todo_list.dto.CategoryDto;
import com.example.todo_list.response.ResponseApi;

public interface CategoryService {

    ResponseApi allCategory();
    ResponseApi addCategory(CategoryDto categoryDto);
    ResponseApi getByIdCategory(Long id);
    ResponseApi deleteCategory(Long id);

}
