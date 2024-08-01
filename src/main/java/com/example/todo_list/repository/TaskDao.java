package com.example.todo_list.repository;

import com.example.todo_list.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDao extends JpaRepository<TaskEntity,Long> {
}
