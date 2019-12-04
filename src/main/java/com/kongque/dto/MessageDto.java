package com.kongque.dto;

import java.util.Date;

public class MessageDto {

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

    /*
    用户id
     */
    private String userId;

    /*
    下发时间
     */
    private Date createTime;

    /*
    推送标识
     */
    private String flag;

    /*
    推送时间
     */
    private Date pushTime;

    /*
    指定推送用户ids
     */
    private String [] userIds;


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

    public String[] getUserIds() {
        return userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public MessageDto() {
    }

    public MessageDto(String userId,String flag) {
        this.userId = userId;
        this.flag = flag;
    }
}
