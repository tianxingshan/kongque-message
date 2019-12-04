package com.kongque.controller.message;

import com.kongque.constants.Constants;
import com.kongque.dao.IMessageDao;
import com.kongque.dto.MessageDto;
import com.kongque.entity.Message;
import com.kongque.service.IMessageService;
import com.kongque.util.HttpClientUtil;
import com.kongque.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 后台消息推送给用户
 */
@RestController
public class MessageController {

    @Autowired
    private IMessageService messageService;


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private IMessageDao dao;


    /**
     * 后台管理下发消息,如果下发的用户在线,便立刻推送
     */
    @PostMapping(value = "/message/push")
    private Result messageToUser(@RequestBody MessageDto dto){
        logger.info("后台下发消息开始:\n消息名字:"+dto.getTheme()+"\n指定用户:"+ Arrays.toString(dto.getUserIds()));
        return messageService.messagePush(dto);
    }

}
