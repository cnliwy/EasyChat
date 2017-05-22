package com.liwy.easychat.utils;

import com.google.gson.Gson;
import com.liwy.easychat.entity.ChatMessage;

/**
 * Created by liwy on 2017/5/19.
 */

public class DataParseUtil {
    /**
     * 解析json数据
     * @param content
     */
    public static ChatMessage parseJson(String content){
        Gson gson = new Gson();
        ChatMessage chatMessage = (ChatMessage)gson.fromJson(content,ChatMessage.class);
        if (chatMessage != null && chatMessage.getAction() != null && !"".equals(chatMessage.getAction())){
            System.out.println(chatMessage.getId() + "：" + content);
            return chatMessage;
        }
        return null;
    }
}
