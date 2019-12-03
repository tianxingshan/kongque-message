package com.kongque.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

public class LogInterceptor  implements HandlerInterceptor{

    private Logger logger = LoggerFactory.getLogger(getClass());


}
