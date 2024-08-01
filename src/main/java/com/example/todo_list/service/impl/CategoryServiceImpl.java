package com.example.todo_list.service.impl;


import com.example.todo_list.repository.CategoryDao;
import com.example.todo_list.dto.CategoryDto;
import com.example.todo_list.entity.CategoryEntity;
import com.example.todo_list.message.Const;
import com.example.todo_list.response.ResponseApi;
import com.example.todo_list.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }
    @Transactional(readOnly = true)
    @Override
    public ResponseApi allCategory() {
        try {
            List<CategoryEntity> allCategory = categoryDao.findAll().stream().filter(category -> category.isStatus()==true).collect(Collectors.toList());
            if (!allCategory.isEmpty())
            {
                return new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS, Optional.of(allCategory)) ;
            }
            return new ResponseApi(Const.STATUS_OK,Const.MSG_NO_CONTENT, Optional.empty()) ;
        }
        catch (Exception e)
        {
            return  new ResponseApi(Const.STATUS_INTERNAL_ERROR,Const.MSG_INTERNAL_ERROR,Optional.empty());
        }
    }

    @Override
    public ResponseApi addCategory(CategoryDto categoryDto) {
        try{
            CategoryEntity categoryEntity=new CategoryEntity();
            categoryEntity.setName(categoryDto.getName());
            categoryEntity.setStatus(true);
            categoryDao.save(categoryEntity);
            return new ResponseApi(Const.STATUS_CREATED,Const.MSG_CREATED,Optional.of(categoryEntity));

        }
        catch (Exception e)
        {return  new ResponseApi(Const.STATUS_INTERNAL_ERROR,Const.MSG_INTERNAL_ERROR,Optional.empty());

        }
    }
    @Transactional(readOnly = true)
    @Override
    public ResponseApi getByIdCategory(Long id) {
        try{
            Optional<CategoryEntity> categoryFound = categoryDao.findById(id);
            if (categoryFound.isPresent())
            {
                return new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS,Optional.of(categoryFound));
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
    public ResponseApi deleteCategory(Long id) {
        try {
            Optional<CategoryEntity>categoryFound = categoryDao.findById(id);
            if (categoryFound.isPresent())
            {
                CategoryEntity categoryEntity = categoryFound.get();
                categoryEntity.setStatus(false);
                return new ResponseApi(Const.STATUS_OK,Const.MSG_SUCCESS,Optional.of(categoryEntity));

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
}
