package com.kongque.service.impl;

import com.kongque.dao.IMessageDao;
import com.kongque.dto.MessageDto;
import com.kongque.dto.WebSockDataDto;
import com.kongque.entity.Message;
import com.kongque.service.IMessageService;
import com.kongque.util.Result;
import com.kongque.util.UUIDUtil;
import com.kongque.ws.KongqueWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MessageServiceImpl  implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource
    private IMessageDao messageDao;

    @Resource
    private KongqueWebSocket kongqueWebSocket;

    @Override
    public Result messagePush(MessageDto dto){

        List<Message> messageList = new ArrayList<>();
        List<String> userIdList = Arrays.asList(dto.getUserIds());
        userIdList.forEach(o->{
            Message message = new Message(dto);
            message.setId(UUIDUtil.getUUID32());
            message.setFlag("0");
            message.setUserId(o);
            message.setCreateTime(new Date());
            messageList.add(message);
        });
        messageDao.insertBatch(messageList);
        logger.info("后台下发消息成功:\n消息名称:"+dto.getTheme()+"\n指定用户:"+Arrays.toString(dto.getUserIds()));

        //处理发送消息,用户对应的消息集合
        WebSockDataDto webSockDataDto = new WebSockDataDto();
        List<Map<String,List<Message>>> pushMapList = new ArrayList<>();
        for (String uid : userIdList) {
            Map<String,List<Message>> pushMap = new HashMap<>();
            List<Message> list = new ArrayList<>();
            for (Message message : messageList) {
                if(uid.equals(message.getUserId())){
                    list.add(message);
                }
            }
            pushMap.put(uid, list);
            pushMapList.add(pushMap);
        }
        webSockDataDto.setMapList(pushMapList);
        kongqueWebSocket.onMessage(webSockDataDto);
        return new Result<>();
    }

}
