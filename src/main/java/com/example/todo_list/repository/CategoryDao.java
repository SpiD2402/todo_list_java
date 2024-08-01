package com.example.todo_list.repository;

import com.example.todo_list.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<CategoryEntity,Long> {



}
