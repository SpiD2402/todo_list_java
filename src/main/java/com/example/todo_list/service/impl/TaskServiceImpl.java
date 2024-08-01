package com.example.todo_list.service.impl;

import com.example.todo_list.repository.CategoryDao;
import com.example.todo_list.repository.TaskDao;
import com.example.todo_list.repository.UserDao;
import com.example.todo_list.dto.TaskDto;
import com.example.todo_list.entity.CategoryEntity;
import com.example.todo_list.entity.TaskEntity;
import com.example.todo_list.entity.UserEntity;
import com.example.todo_list.message.Const;
import com.example.todo_list.response.ResponseApi;
import com.example.todo_list.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;

    public TaskServiceImpl(TaskDao taskDao, UserDao userDao, CategoryDao categoryDao) {
        this.taskDao = taskDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
    }
    @Transactional(readOnly = true)
    @Override
    public ResponseApi allTask() {
        try {
            List<TaskEntity> all = taskDao.findAll().stream().filter(task -> task.getStatus().equals("Pending")).collect(Collectors.toList());
            if (!all.isEmpty())
            {
                return new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS, Optional.of(taskDao.findAll())) ;
            }
            return new ResponseApi(Const.STATUS_OK,Const.MSG_NO_CONTENT, Optional.empty()) ;
        }
        catch (Exception e)
        {
            return  new ResponseApi(Const.STATUS_INTERNAL_ERROR,Const.MSG_INTERNAL_ERROR,Optional.empty());
        }

    }
    @Transactional(readOnly = true)
    @Override
    public ResponseApi getBYIdTask(Long id) {
        try{
            Optional<TaskEntity> taskFound = taskDao.findById(id);
            if (taskFound.isPresent())
            {
                return new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS,Optional.of(taskFound));
            }
            else {
                return new ResponseApi(Const.STATUS_NOT_FOUND,Const.MSG_NOT_FOUND,Optional.empty());
            }

        }
        catch (Exception e)
        {
            return  new ResponseApi(Const.STATUS_INTERNAL_ERROR,Const.MSG_INTERNAL_ERROR,Optional.empty());
        }
    }


    @Transactional
    @Override
    public ResponseApi addTask(TaskDto taskDto) {
        try{
            TaskEntity task=toEntity(taskDto);
            taskDao.save(task);
            return new ResponseApi(Const.STATUS_CREATED,Const.MSG_CREATED,Optional.of(task));

        }
        catch (Exception e)
        {return  new ResponseApi(Const.STATUS_INTERNAL_ERROR,Const.MSG_INTERNAL_ERROR,Optional.empty());

        }
    }

    @Transactional
    @Override
    public ResponseApi deleteTask(Long id) {
        try {
            Optional<TaskEntity>taskFound = taskDao.findById(id);
            if (taskFound.isPresent())
            {
                TaskEntity taskEntity = taskFound.get();
                taskEntity.setStatus("Completed");
                return new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS,Optional.of(taskFound));

            }
            else {
                return new ResponseApi(Const.STATUS_NOT_FOUND,Const.MSG_NOT_FOUND,Optional.empty());

            }
        }
        catch (Exception e)
        {
            return  new ResponseApi(Const.STATUS_INTERNAL_ERROR,Const.MSG_INTERNAL_ERROR,Optional.empty());

        }
    }



    public TaskEntity toEntity(TaskDto taskDto)
    {
        UserEntity user = userLogueado();
        Optional<CategoryEntity> categoryEntity = categoryDao.findById(taskDto.getCategory_id());

        if (!categoryEntity.isPresent())
        {
            throw  new NoSuchElementException("No existe la categoria");
        }

        TaskEntity task = new TaskEntity();
        task.setUser(userLogueado());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCategory(categoryEntity.get());
        task.setStatus("Pending");
        task.setCreate_at(getTime());


        return task;
    }

    public Timestamp getTime()
    {
        Long currentTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTime);
        return timestamp;
    }


    public UserEntity userLogueado ()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||  !authentication.isAuthenticated())
        {
            throw new IllegalStateException("User is not authenticated");

        }

        Optional<UserEntity> userEncontrado =userDao.findByUsername(authentication.getName());
        return  userEncontrado.get();
    }




}
