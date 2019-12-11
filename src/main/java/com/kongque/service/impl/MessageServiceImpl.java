package com.kongque.service.impl;

import com.kongque.dao.IMessageDao;
import com.kongque.dto.MessageDto;
import com.kongque.dto.WebSockDataDto;
import com.kongque.entity.Message;
import com.kongque.service.IMessageService;
import com.kongque.util.DataRunable;
import com.kongque.util.Result;
import com.kongque.util.UUIDUtil;
import com.kongque.ws.KongqueWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageServiceImpl  implements IMessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource
    private IMessageDao messageDao;

    @Resource
    private KongqueWebSocket kongqueWebSocket;

    @Override
    public Result messagePush(MessageDto dto){


        //在线用户
        ConcurrentHashMap<String, KongqueWebSocket> webSocketMap = KongqueWebSocket.webSocketMap;

        List<Message> messageList = new ArrayList<>();
        List<Message> onlinePushedUserMessageList = new ArrayList<>();//在线用户需要推送的消息集合
        List<String> userIdList = new ArrayList<>(Arrays.asList(dto.getUserIds()));//要推送的用户
        List<String> onlineUserIds = new ArrayList<>();//在线用户
        List<String> onlinePushedUserIds = new ArrayList<>();//在线用户中需要推送的用户集合

        for (String accountId : webSocketMap.keySet()){
            onlineUserIds.add(accountId);
        }

        for(String uid : userIdList){
            for(String onlineUid : onlineUserIds){
                if(uid.equals(onlineUid)){
                    onlinePushedUserIds.add(uid);
                }
            }
        }
        //组装未推送的消息集合
        userIdList.removeAll(onlinePushedUserIds);
        Date date = new Date();
        userIdList.forEach(i->{
            Message message = new Message(dto);
            message.setFlag("0");
            message.setCreateTime(date);
            message.setUserId(i);
            messageList.add(message);
        });
        //组装推送的消息集合
        onlinePushedUserIds.forEach(i->{
            Message message = new Message(dto);
            message.setFlag("1");
            message.setCreateTime(date);
            message.setUserId(i);
            message.setPushTime(date);
            onlinePushedUserMessageList.add(message);
        });
        messageList.addAll(onlinePushedUserMessageList);
        DataRunable<List<Message>> insertRunable = new DataRunable<>(messageList, l -> {
            messageDao.insertBatch(l);
        });
        CompletableFuture<Void> insertFutrure = CompletableFuture.runAsync(insertRunable);

        //处理要推送的消息(下发消息中指定的用户且在线,则是要推送的)
        WebSockDataDto webSockDataDto = new WebSockDataDto();
        List<Map<String,List<Message>>> pushMapList = new ArrayList<>();
        onlinePushedUserMessageList.forEach(o->{
            Map<String,List<Message>> pushMap = new HashMap<>();
            List<Message> mList = new ArrayList<>();
            mList.add(o);
            pushMap.put(o.getUserId(),mList);
            pushMapList.add(pushMap);
        });
        webSockDataDto.setMapList(pushMapList);
        DataRunable<WebSockDataDto> pushRunnable = new DataRunable<>(webSockDataDto, d -> {
            kongqueWebSocket.onMessage(webSockDataDto);
        });
        CompletableFuture<Void> pushFutrure = CompletableFuture.runAsync(pushRunnable);
        CompletableFuture.allOf(insertFutrure,pushFutrure).join();
        logger.info("后台下发消息成功:\n消息名称:"+dto.getTheme()+"\n指定用户:"+Arrays.toString(dto.getUserIds()));
        return new Result<>();
    }

    @Override
    public Result<List<Message>> getList(MessageDto dto) {
        Result result = new Result();
        if(dto.getPage()==null){
            dto.setPage(1);
            dto.setPageSize(10);
        }
        Integer idex = (dto.getPage()-1)*dto.getPageSize();
        dto.setPage(idex);
        List<Message> list = messageDao.getList(dto);
        int count = messageDao.getCount(dto);
        result.setTotal(count);
        result.setReturnData(list);
        return result;
    }

    @Override
    public void test() {

        Message m = new Message();
        m.setContent("通过");
        m.setId(UUIDUtil.getUUID32());
        messageDao.insert(m);

        Message message1 = new Message();
        message1.setContent("失败水电费水电费个1234545456s失败");
        message1.setId(UUIDUtil.getUUID32());
        messageDao.insert(message1);
    }

}
