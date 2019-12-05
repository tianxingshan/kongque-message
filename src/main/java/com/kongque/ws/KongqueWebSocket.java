package com.kongque.ws;


import com.alibaba.fastjson.JSONArray;
import com.kongque.dao.IMessageDao;
import com.kongque.dto.MessageDto;
import com.kongque.dto.WebSockDataDto;
import com.kongque.entity.Message;
import com.kongque.entity.MessageDecoder;
import com.kongque.entity.MessageEncoder;
import com.kongque.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket服务
 */
@ServerEndpoint(value = "/ws/{accountId}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
@Component
public class KongqueWebSocket {

    private Logger log = LoggerFactory.getLogger(KongqueWebSocket.class);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
     */
    public static ConcurrentHashMap<String, KongqueWebSocket> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;


    private IMessageDao messageDao;


    private static ConfigurableApplicationContext applicationContext;

    public static void setApplicationContext(ConfigurableApplicationContext applicationContext) {
        KongqueWebSocket.applicationContext = applicationContext;
    }

    /**
     * 连接建立成功调用的方法
     * 说明: 建立通信时,检查是否有未推送消息,有则推送,并验证token
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "accountId") String accountId) {
        this.session = session;
        webSocketMap.put(accountId, this);
        log.info("用户accountId=" + accountId + "开始接入socket,当前通信人数为:" + webSocketMap.size());
        messageDao = applicationContext.getBean("IMessageDao", IMessageDao.class);
        MessageDto dto = new MessageDto();
        dto.setUserId(accountId);
        dto.setFlag("0");
        List<Message> list = messageDao.getList(dto);
        if (list != null && list.size() > 0) {
            WebSockDataDto webSockDataDto = new WebSockDataDto();
            List<Map<String,List<Message>>> mapList = new ArrayList<>();
            Map<String,List<Message>> map = new HashMap<>();
            map.put(accountId, list);
            mapList.add(map);
            webSockDataDto.setMap(map);
            webSockDataDto.setMapList(mapList);
            //推送给指定用户
            onMessage(webSockDataDto);
        }

    }

    /**
     * 批量更新消息
     *
     * @param list
     */
    public void updateMessage(List<Message> list) {
        list.forEach(m -> {
            m.setFlag("1");
            m.setPushTime(new Date());
        });
        messageDao = applicationContext.getBean("IMessageDao", IMessageDao.class);
        messageDao.updateBatch(list);
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam(value = "accountId") String accountId) {
            webSocketMap.remove(accountId);
            log.info("websocket关闭连接");
            log.info("用户下线,accountId:" + accountId + "目前在线用户数 : " + webSocketMap.size());
    }

    /**
     * (监听方法,前后端的onMessage有变动便进行通信)
     * 推送消息 : 遍历推送的消息,遍历在线用户,在线则推送
     * @param dataDto
     */
    @OnMessage
    public void onMessage(WebSockDataDto dataDto) {

        List<Map<String, List<Message>>> mapList = dataDto.getMapList();
        for (Map<String, List<Message>> map :mapList) {
            for (String accountId :map.keySet()) {
                KongqueWebSocket kongqueWebSocket = webSocketMap.get(accountId);
                if(kongqueWebSocket !=null){
                    kongqueWebSocket.sendObj(map.get(accountId));
                    //更新数据
                    updateMessage(map.get(accountId));
                    log.info("向用户accountId=" + accountId + "推送消息成功:" + "\n" + "消息:" + JSONArray.toJSONString(map.get(accountId)));
                }
            }
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Throwable error) {
        log.info(error.toString());
    }

    /**
     * 发送对象到客户端
     */
    private void sendObj(List<Message> list) {
        try {
            this.session.getBasicRemote().sendObject(new Result<>(list));
        } catch (EncodeException | IOException e) {
            log.error("消息推送失败", e);
        }
    }

    /**
     * 发送文本到客户端
     */
    private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.info("发送消息错误");
        }
    }
}
