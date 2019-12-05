package com.kongque.config;

import com.kongque.interceptor.WebSocketHandlerShakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

import javax.annotation.Resource;

/**
 * 注册拦截器
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketHandShakeConfig  implements WebSocketMessageBrokerConfigurer {


    @Resource
    private WebSocketHandlerShakeInterceptor webSocketHandlerShakeInterceptor;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws/*")
                .addInterceptors(webSocketHandlerShakeInterceptor)
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
