package com.kongque.service;

import com.kongque.model.ModelForLogin;

/*
登录验证
 */
public interface LoginService {

    /*
    从reids获取用户信息,如果redis失效,更新数据库token=null
     */
     ModelForLogin getLoginInfoFromRedis(String token);


}
