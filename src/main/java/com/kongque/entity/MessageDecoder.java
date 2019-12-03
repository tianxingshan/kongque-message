package com.kongque.entity;

import com.alibaba.fastjson.JSON;
import com.kongque.dto.WebSockDataDto;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * 解密参数(接收参数时解密,例如onmessage等接收参数)
 */
public class MessageDecoder implements Decoder.Text<WebSockDataDto>{
    @Override
    public WebSockDataDto decode(String s) throws DecodeException {
        return  JSON.parseObject(s, WebSockDataDto.class);
    }
    @Override
    public boolean willDecode(String s) {
        return true;
    }
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
