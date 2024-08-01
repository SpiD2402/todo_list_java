package com.example.todo_list.repository;

import com.example.todo_list.entity.PriorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityDao extends JpaRepository<PriorityEntity,Long> {
}
