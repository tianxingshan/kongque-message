package com.kongque.dto;

import com.kongque.entity.Message;

import java.util.List;
import java.util.Map;


public class WebSockDataDto {

    /*
        onmessage方法是否处理token标识 true-处理 false-不处理

      */
    private Boolean dealTokenFlag =false;

    private String accountId;

    private String tokenCheckResult;

    private List<Message> messageList;

    private Map<String,List<Message>> map;

    private List<Map<String,List<Message>>> mapList;

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public Map<String, List<Message>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<Message>> map) {
        this.map = map;
    }

    public List<Map<String, List<Message>>> getMapList() {
        return mapList;
    }

    public void setMapList(List<Map<String, List<Message>>> mapList) {
        this.mapList = mapList;
    }

    public Boolean getDealTokenFlag() {
        return dealTokenFlag;
    }

    public void setDealTokenFlag(Boolean dealTokenFlag) {
        this.dealTokenFlag = dealTokenFlag;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTokenCheckResult() {
        return tokenCheckResult;
    }

    public void setTokenCheckResult(String tokenCheckResult) {
        this.tokenCheckResult = tokenCheckResult;
    }
}
