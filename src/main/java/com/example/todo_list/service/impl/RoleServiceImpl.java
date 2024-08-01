package com.example.todo_list.service.impl;

import com.example.todo_list.repository.RoleDao;
import com.example.todo_list.dto.RoleDto;
import com.example.todo_list.entity.RoleEntity;

import com.example.todo_list.message.Const;
import com.example.todo_list.response.ResponseApi;
import com.example.todo_list.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl  implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    @Transactional(readOnly = true)
    @Override
    public ResponseApi allRole() {
        try {
            List<RoleEntity> allRole = roleDao.findAll().stream().filter(role -> role.isStatus()==true).collect(Collectors.toList());
            if (!allRole.isEmpty())
            {
                return new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS, Optional.of(allRole)) ;
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
    public ResponseApi addRole(RoleDto roleDto) {
        try{
            RoleEntity roleEntity=toEntity(roleDto);
            roleDao.save(roleEntity);
            return new ResponseApi(Const.STATUS_CREATED,Const.MSG_CREATED,Optional.of(roleEntity));

        }
        catch (Exception e)
        {return  new ResponseApi(Const.STATUS_INTERNAL_ERROR,Const.MSG_INTERNAL_ERROR,Optional.empty());

        }

    }

    @Transactional(readOnly = true)
    @Override
    public ResponseApi getByIdRole(Long id) {
        try{
            Optional<RoleEntity> roleFound = roleDao.findById(id);
            if (roleFound.isPresent())
            {
                return new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS,Optional.of(roleFound));
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
    public ResponseApi deleteRole(Long id) {
        try {
            Optional<RoleEntity>roleFound = roleDao.findById(id);
            if (roleFound.isPresent())
            {
                RoleEntity roleEntity = roleFound.get();
                roleEntity.setStatus(false);
                return new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS,Optional.of(roleEntity));

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


    public RoleEntity toEntity(RoleDto roleDto)
    {
        RoleEntity roleEntity= new RoleEntity();
        roleEntity.setName("ROLE_"+roleDto.getName());
        roleEntity.setStatus(true);
        return  roleEntity;
    }
}
