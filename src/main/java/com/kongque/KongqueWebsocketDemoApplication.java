package com.kongque;

import com.kongque.ws.KongqueWebSocket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.kongque.dao")
public class KongqueWebsocketDemoApplication{

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KongqueWebsocketDemoApplication.class, args);
        KongqueWebSocket.setApplicationContext(context);
    }

}
