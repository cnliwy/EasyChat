package com.liwy.easychat.entity;

/**
 * Created by liwy on 2017/5/18.
 */

public class DataMessage {
    private int type;//1系统 2好友 3消息
    private String content;//消息内容

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
