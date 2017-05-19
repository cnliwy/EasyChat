package com.liwy.easychat.callback;

import com.liwy.easychat.entity.ChatMessage;

/**
 * Created by liwy on 2017/5/19.
 */

public interface RosterMessageListener {
    public void rosters(ChatMessage chatMessage);

    public void rosterUpdate(ChatMessage chatMessage);
}
