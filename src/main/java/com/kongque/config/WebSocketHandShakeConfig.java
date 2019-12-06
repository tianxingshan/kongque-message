package com.kongque.config;

import com.kongque.interceptor.WebSocketHandlerShakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 注册拦截器
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketHandShakeConfig implements WebSocketMessageBrokerConfigurer {

    // @Autowired
    // private WebSocketHandlerShakeInterceptor webSocketHandlerShakeInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {


        //开启/myEndPoint端点
        registry.addEndpoint("/ws/**")
                .addInterceptors(new WebSocketHandlerShakeInterceptor())
                //允许跨域访问
                .setAllowedOrigins("*")
                //使用sockJS
                .withSockJS();
    }

}