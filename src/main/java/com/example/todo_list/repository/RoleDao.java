package com.example.todo_list.repository;

import com.example.todo_list.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<RoleEntity,Long> {
}
