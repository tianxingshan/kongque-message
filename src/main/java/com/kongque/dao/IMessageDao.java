package com.kongque.dao;

import com.kongque.dto.MessageDto;
import com.kongque.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMessageDao {

    /*
    添加消息
     */
    int insert(Message message);

    /*
    根据参数获取消息集合
     */
    List<Message> getList(MessageDto dto);

    /*
    获取总数
     */
    int getCount(MessageDto dto);

    /*
    批量添加
     */
    int insertBatch(List<Message> list);

    /*
    批量更新
     */
    int updateBatch(List<Message> list);
}
