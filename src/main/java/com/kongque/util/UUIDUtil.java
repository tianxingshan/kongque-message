package com.kongque.util;

import java.util.UUID;

public class UUIDUtil {

    /*
    生成32位uuid
     */
    public static String getUUID32(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

}
