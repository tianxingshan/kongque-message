package com.kongque.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kongque.dto.MessageDto;
import com.kongque.util.UUIDUtil;

import java.util.Date;

public class Message {

    /**
     * 主键
     */
    private String id;

    /**
     * 主题
     */
    private String  theme;
    /**
     * 内容
     */
    private String  content;
    /**
     * 下发时间
     */
    private Date createTime;

    /*
    推送用户id
     */
    private String userId;

    /*
    推送标识 0-未推送 1-已推送
     */
    private String flag;

    /*
    推送时间
     */
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pushTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Message() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Message(MessageDto dto) {
        this.id= UUIDUtil.getUUID32();
        this.theme = dto.getTheme();
        this.content=dto.getContent();
    }
}
