package com.example.todo_list.service;

import com.example.todo_list.dto.UserDto;
import com.example.todo_list.response.ResponseApi;



public interface UserService {

    ResponseApi allUser();

    ResponseApi addUser(UserDto userDto ) throws Exception;
    ResponseApi getById(Long id);

    ResponseApi deleteUser(Long id);
    ResponseApi loadByUser();

}
