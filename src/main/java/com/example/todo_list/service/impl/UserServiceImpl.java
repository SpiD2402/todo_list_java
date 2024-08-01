package com.example.todo_list.service.impl;

import com.example.todo_list.repository.RoleDao;
import com.example.todo_list.repository.UserDao;
import com.example.todo_list.dto.UserDto;
import com.example.todo_list.entity.RoleEntity;
import com.example.todo_list.entity.UserEntity;
import com.example.todo_list.message.Const;
import com.example.todo_list.response.ResponseApi;
import com.example.todo_list.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private  final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleDao roleDao;
    private final TaskServiceImpl taskService;

    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder, RoleDao roleDao, TaskServiceImpl taskService) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleDao = roleDao;
        this.taskService = taskService;
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseApi allUser() {
        try {
            List<UserEntity> all = userDao.findAll().stream().filter(user -> user.isStatus() ==true).collect(Collectors.toList());
            if (!all.isEmpty())
            {
                return new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS, Optional.of(userDao.findAll())) ;
            }
            return new ResponseApi(Const.STATUS_OK,Const.MSG_NO_CONTENT, Optional.empty()) ;
        }
        catch (Exception e)
        {
            return  new ResponseApi(Const.STATUS_INTERNAL_ERROR,Const.MSG_INTERNAL_ERROR,Optional.empty());
        }
    }

    @Transactional
    @Override
    public ResponseApi addUser(UserDto userDto) throws Exception {
        if (userDao.existsByEmailOrUsername(userDto.getEmail(),userDto.getUsername()) >0)
        {
            throw  new Exception("Username or Email exists");
        }
        if (!roleDao.findById(userDto.getRole_id()).isPresent())
        {
            throw  new Exception("El rol asignar no existe");

        }
        try{
            UserEntity user = toEntity(userDto);
            userDao.save(user);
            return new ResponseApi(Const.STATUS_CREATED,Const.MSG_CREATED,Optional.of(user));

        }
        catch (Exception e)
        {return  new ResponseApi(Const.STATUS_INTERNAL_ERROR,e.getMessage(),Optional.empty());

        }

    }

    @Transactional(readOnly = true)
    @Override
    public ResponseApi getById(Long id) {
        try{
            Optional<UserEntity> userFound = userDao.findById(id);
            if (userFound.isPresent())
            {
                return new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS,Optional.of(userFound));
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
    public ResponseApi deleteUser(Long id) {
        try {
            Optional<UserEntity>userFound = userDao.findById(id);
            if (userFound.isPresent())
            {
                UserEntity userEntity = userFound.get();
                userEntity.setStatus(false);
                return new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS,Optional.of(userEntity));

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

    @Override
    public ResponseApi loadByUser() {
        UserEntity user = taskService.userLogueado();
        Optional<UserEntity> useData = userDao.findByUsername(user.getUsername());
        return  new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS,useData);

    }

    public String encryptPassword(String password)
    {
        String passwordEncoder = bCryptPasswordEncoder.encode(password);
        return  passwordEncoder;
    }

    public UserEntity toEntity(UserDto userDto)
    {
        Optional<RoleEntity> roleEntity = roleDao.findById(userDto.getRole_id());
        UserEntity user = new UserEntity();
        user.setRole(roleEntity.get());
        user.setPassword(encryptPassword(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setLast_name(userDto.getLast_name());
        user.setEmail(userDto.getEmail());
        user.setStatus(true);

        return  user;
    }


}
