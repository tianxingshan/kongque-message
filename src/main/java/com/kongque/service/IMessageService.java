package com.kongque.service;

import com.kongque.dto.MessageDto;
import com.kongque.entity.Message;
import com.kongque.util.Result;

import java.util.List;

/**
 * 消息服务
 */
public interface IMessageService {

    /**
     * 后台指定用户下发消息,如果用户在线,现在发送
     * @return
     */
    Result messagePush(MessageDto dto);



}
