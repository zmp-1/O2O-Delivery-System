package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * 微信登陆接口
 */
public interface UserService {



    User wxlogin(UserLoginDTO userLoginDTO);
}
