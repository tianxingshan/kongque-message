package com.kongque;

import com.alibaba.fastjson.JSONObject;
import com.kongque.ws.KongqueWebSocket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan("com.kongque.dao")
public class KongqueWebsocketDemoApplication{

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KongqueWebsocketDemoApplication.class, args);
        KongqueWebSocket.setApplicationContext(context);

        //查看默认数据源
/*        DataSource bean = context.getBean(DataSource.class);
        System.out.println(JSONObject.toJSON(bean).toString());*/
    }

}
