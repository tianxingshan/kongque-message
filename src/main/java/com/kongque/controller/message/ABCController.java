package com.kongque.controller.message;

import com.kongque.util.RedisUtil;
import com.kongque.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ABCController {
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping(value = "/a")
    public Result a(){
        redisUtil.set("s01","sws-01");
        return new Result();
    }
}
