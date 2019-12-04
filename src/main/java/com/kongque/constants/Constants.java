package com.kongque.constants;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    public  static class RESULT_CODE{

        /**
         * 请求中没有登录参数token
         */
        public  static final String UN_AUTHORIZED = "403";
    }


    /*
    账号地址
     */
    public static String url;
    @Value("${url}")
    public void setUrl(String url){this.url = url;}

}
