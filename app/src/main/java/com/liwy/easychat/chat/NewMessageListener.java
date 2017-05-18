package com.liwy.easychat.chat;

/**
 * 新消息回调
 * Created by liwy on 2017/5/18.
 */

public interface NewMessageListener {
    public void messageArrived(String id, String content);
}
