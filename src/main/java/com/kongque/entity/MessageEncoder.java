package com.kongque.entity;

import com.alibaba.fastjson.JSONObject;
import com.kongque.dto.WebSockDataDto;
import com.kongque.util.Result;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * 加密参数(返回时使用)
 */
public class MessageEncoder implements Encoder.Text<Result>{


    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String encode(Result result) throws EncodeException {
        return JSONObject.toJSONString(result);
    }
}
