package com.example.todo_list.repository;

import com.example.todo_list.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDao extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByUsername(String username);

    @Query("select count(t) from UserEntity t where t.username=:username or t.email=:email")
    Long existsByEmailOrUsername(String email,String username);

}
