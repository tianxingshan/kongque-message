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
     * 后台指定用户下发消息,并推送给在线用户
     * @return
     */
    Result messagePush(MessageDto dto);

    /*
    查看已经推送的消息
     */
    Result<List<Message>> getList(MessageDto dto);
}
