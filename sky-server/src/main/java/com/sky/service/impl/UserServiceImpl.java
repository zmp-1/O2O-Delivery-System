package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class UserServiceImpl  implements UserService {

    /**
     * 微信登陆
     * @param userLoginDTO
     * @return
     */

    //微信服务接口地址
    public static final String WX_LOGIN="https://api.weixin.qq.com/sns/jscode2session";
@Autowired
 private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;


    public User wxlogin(UserLoginDTO userLoginDTO) {

String  openid=getOpenid(userLoginDTO.getCode());
        log.info("前端返回的code：{}",userLoginDTO.getCode());
log.info("微信登陆返回结果：{}",openid);
if(openid==null){
    throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
}

        //判断openid是否为空，如果为空，表示登陆失败，抛出业务异常
        //判断当前用户的openid 是否在我们用户表里面，如果不是，他是新用户，自动完成注册，自己分装一个user对象把她抱此u你在用户表里
        User user = userMapper.getByOpenid(openid);
if(user==null){
   user= User.builder()
            .openid(openid)
            .createTime(LocalDateTime.now())
            .build();
    userMapper.insert(user);

}


        return user;
    }


    /**
     *
     * 调用微信接口服务，来获取openid
     * @param code
     * @return
     */
    private String getOpenid(String code){


        Map<String, String> map=new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json=  HttpClientUtil.doGet(WX_LOGIN,map);
log.info("微信服务器返回的原始数据：{}",json);
        //调用微信服务器的接口，赖活得当前用户的openid
        JSONObject jsonObject =JSON.parseObject(json);

        String openid = jsonObject.getString("openid");

return openid;


    }
}
