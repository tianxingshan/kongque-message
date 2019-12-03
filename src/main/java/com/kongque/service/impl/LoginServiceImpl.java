package com.kongque.service.impl;

import com.kongque.model.ModelForLogin;
import com.kongque.service.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl  implements LoginService {
    @Override
    public ModelForLogin getLoginInfoFromRedis(String token) {
        return null;
    }
}
