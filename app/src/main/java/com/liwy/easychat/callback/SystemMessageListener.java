package com.liwy.easychat.callback;

import com.liwy.easychat.entity.ChatMessage;

/**
 * Created by liwy on 2017/5/18.
 */

public interface SystemMessageListener {
    public void loginCallback(ChatMessage chatMessage);
}
