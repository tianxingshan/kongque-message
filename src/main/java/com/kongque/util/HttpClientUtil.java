package com.kongque.util;

import org.apache.catalina.core.ApplicationContext;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

@Component
public class HttpClientUtil {

   private static ApplicationContext applicationContext;


    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);



    public static String doPost(String url, Map<String,String> paramMap){
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = null;
        String result = null;
        //创建客户端
        httpClient = HttpClients.createDefault();
        //创建远程实例
        HttpPost httpPost = new HttpPost(url);
        //配置请求参数
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000).setSocketTimeout(10000).setConnectTimeout(1000).build();
        //为http实例配置设置
        httpPost.setConfig(requestConfig);
        //设置请求头
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        //封装post请求参数
        if(paramMap!=null && paramMap.size() > 0){
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            for (Map.Entry<String,String> entry:paramMap.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
            //为httppost设置封装好的请求参数
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            } catch (UnsupportedEncodingException e) {
                logger.info("doPost参数异常...");
                e.printStackTrace();
            }
        }
        //为httpPost
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();
            //请求结果转换成str
            result =  EntityUtils.toString(responseEntity);
        } catch (IOException e) {
            logger.info("dopost请求异常...");
            e.printStackTrace();
        } finally {
            if(httpResponse!=null){
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(httpClient !=null){
                    try {
                        httpClient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return  result;
    }

}
