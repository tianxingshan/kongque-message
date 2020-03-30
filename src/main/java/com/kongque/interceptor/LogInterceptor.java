package com.kongque.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.kongque.constants.Constants;
import com.kongque.util.HttpClientUtil;
import com.kongque.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class LogInterceptor  implements HandlerInterceptor{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //获取token
       /* String token = request.getHeader("token");
        response.setContentType("application/json");//设置响应的内容类型json字符串
        response.setHeader("Access-Control-Allow-origin", "*");//设置允许浏览器允许跨域访问
        response.setCharacterEncoding("utf-8");
        if(StringUtils.isEmpty(token)){
            PrintWriter out = response.getWriter();//从响应实体类中获取输出流
            out.write(JSONObject.toJSON(new Result<>(Constants.RESULT_CODE.UN_AUTHORIZED,"请求失败,token未上传 !")).toString());
            out.flush();//释放输出流缓冲区
            return false;
        }

        //校验token  使用httpclient获取/springcloud
        Map<String,String> verifyMap =new HashMap<>();
        verifyMap.put("token", token);
        verifyMap.put("sysId", request.getHeader("sysId"));
        String s = HttpClientUtil.doGet(Constants.url, verifyMap);
        JSONObject js = JSONObject.parseObject(s);
        if(!js.get("returnCode").equals("200")){
            logger.error("登录校验失败" + verifyMap);
            PrintWriter out = response.getWriter();
            out.write(s);
            out.flush();
            return  false;
        }
        logger.info("校验成功");*/
        return true;
    }


}
