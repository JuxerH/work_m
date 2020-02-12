package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel getUserById(Integer id)throws BusinessException;

    void register(UserModel userModel) throws BusinessException;

    UserModel validateLogin(String telphone, String password) throws BusinessException;

    List<UserModel> listAllUser();

    boolean deleteUserById(Integer userId);
}
