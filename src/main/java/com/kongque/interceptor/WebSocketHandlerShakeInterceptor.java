package com.kongque.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.kongque.constants.Constants;
import com.kongque.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.HashMap;
import java.util.Map;

/*
websocket拦截器
 */
//@Component
public class WebSocketHandlerShakeInterceptor implements HandshakeInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(WebSocketHandlerShakeInterceptor.class);

    /*
    握手前
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)request;
        String token = servletRequest.getServletRequest().getParameter("token");
        Map<String,String> verifyMap = new HashMap<>();
        verifyMap.put("token", token);
        String resp = HttpClientUtil.doGet(Constants.url, verifyMap);
        JSONObject js = JSONObject.parseObject(resp);
        if(!js.get("returnCode").equals("200")){
            logger.info(resp);
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("握手后");
    }

    public WebSocketHandlerShakeInterceptor() {
    }
}
