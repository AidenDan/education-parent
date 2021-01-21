package com.aiden.commonBase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-1-21 22:19:34
 */

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入操作自动填充 gmtCreate gmtModified属性字段
     *
     * @param metaObject metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }

    /**
     * 更新操作自动填充gmtModified属性字段
     *
     * @param metaObject metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
