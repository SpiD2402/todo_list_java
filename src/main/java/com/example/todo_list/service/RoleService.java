package com.example.todo_list.service;

import com.example.todo_list.dto.RoleDto;
import com.example.todo_list.response.ResponseApi;

public interface RoleService {

    ResponseApi allRole();
    ResponseApi addRole(RoleDto roleDto);
    ResponseApi getByIdRole(Long id);

    ResponseApi deleteRole(Long id);


}
