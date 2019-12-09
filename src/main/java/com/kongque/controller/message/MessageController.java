package com.kongque.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.kongque.dao.IMessageDao;
import com.kongque.dto.MessageDto;
import com.kongque.service.IMessageService;
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

    /*
    获取用户历史消息集合
    @Parms : 用户id : userId page:页码 pageSize:每页条数
     */
    @GetMapping(value = "/message/getHisMessageList")
    public Result getList(MessageDto dto){
        logger.info("查看用户历史消息参数 : "+JSONObject.toJSONString(dto));
        dto.setFlag("1");
        return  messageService.getList(dto);
    }


}
