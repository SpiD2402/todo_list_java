package com.example.todo_list.service;

import com.example.todo_list.dto.TaskDto;
import com.example.todo_list.response.ResponseApi;



public interface TaskService {

    ResponseApi allTask();

    ResponseApi getBYIdTask(Long id);




    ResponseApi addTask(TaskDto taskDto);

    ResponseApi deleteTask(Long id);

}
