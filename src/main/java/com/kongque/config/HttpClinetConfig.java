package com.kongque.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Configuration
@PropertySource(value = {"classpath:httpclient.properties"},encoding = "utf-8")
public class HttpClinetConfig {

    /*
    最大连接数
     */
    public  static Integer maxTotal;
    @Value(value = "${http.maxTotal}")
    public  void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    /*
    并发数
    */
    public static Integer defaultMaxPreRoute;
    @Value("${http.defaultMaxPreRoute}")
    public  void setDefaultMaxPreRoute(Integer defaultMaxPreRoute) {
        this.defaultMaxPreRoute = defaultMaxPreRoute;
    }

    /*
    创建连接最长时间
    */
    public static Integer connectTimeout;
    @Value("${http.connectTimeout}")
    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /*
   从连接池获取连接的最长时间
    */
    public static Integer connectionRequestTimeOut;
    @Value("${http.connectTimeout}")
    public  void setConnectionRequestTimeOut(Integer connectionRequestTimeOut) {
        this.connectionRequestTimeOut = connectionRequestTimeOut;
    }

    /*
     数据传输的最长时间
     */

    public static Integer socketTimeOut;
    @Value("${http.socketTimeout}")
    public  void setSocketTimeOut(Integer socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    /*s
        提交请求前测试连接是否可用
         */

    public static boolean staleConnectCheckEnabled;
    @Value("${http.staleConnectionCheckEnabled}")
    public  void setStaleConnectCheckEnabled(boolean staleConnectCheckEnabled) {
        this.staleConnectCheckEnabled = staleConnectCheckEnabled;
    }

    /*
    创建连接池管理器,设置最大连接数,并发连接数
     */
    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager(){
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPreRoute);
        return  poolingHttpClientConnectionManager;
    }

    /*
    创建连接池,并接连连接池管理器
    @Qualifier作用:指定参数谁合法的(PoolingHttpClientConnectionManager有多个实现,使用次注解,表明引用指定的参数)
     */
    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager poolingHttpClientConnectionManager){
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();//实例化(new方法不能使用,构造方法是受保护的)
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
        return httpClientBuilder;
    }

    /*
    创建http客户端 ,并注入该方法连接池实例(写请求的时候会用到)
     */
    @Bean(name = "httpClient")
    public CloseableHttpClient getCloseableHttpClient( @Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder){
        CloseableHttpClient httpClient = httpClientBuilder.build();
        return httpClient;
    }

    /**
     * 创建http请求的配置对象(写请求的时候会用到)
     */
    @Bean(name = "requestConfig")
    public RequestConfig getRequestConfig(@Qualifier("builder") RequestConfig.Builder builder){
        return  builder.build();
    }

    /*
    *builder是RequestConfig的一个内部类,用来设置请求连接的配置
    *用RequestConfig的coutom方法创建builder实例
    */
    @Bean(name = "builder")
    public RequestConfig.Builder getBuilder(){
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectTimeout(connectTimeout);
        builder.setConnectionRequestTimeout(connectionRequestTimeOut);
        builder.setSocketTimeout(socketTimeOut);
        return  builder;
    }


}
