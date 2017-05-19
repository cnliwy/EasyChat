package com.liwy.easychat.callback;

import com.liwy.easychat.entity.ChatMessage;

/**
 * 新消息回调
 * Created by liwy on 2017/5/18.
 */

public interface ChatMessageListener {
    public void messageArrived(ChatMessage chatMessage);
}
