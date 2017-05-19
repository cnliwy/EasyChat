package com.liwy.easychat.chat;

import com.google.gson.Gson;
import com.liwy.easychat.entity.ChatMessage;

/**
 * Created by liwy on 2017/5/18.
 */

public class MessageUtil {
//    private static MessageUtil instance;
//    public static MessageUtil getInstance(){
//        if (instance == null){
//            synchronized (MessageUtil.class){
//                if (instance == null){
//                    instance = new MessageUtil();
//                    return instance;
//                }
//                return instance;
//            }
//        }
//        return instance;
//    }


    public static String processMessage(ChatMessage chatMessage){
        Gson gson = new Gson();
        return gson.toJson(chatMessage);
    }

    // 生成登录json
    public static String processLogin(String id){
        Gson gson = new Gson();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(id);
        chatMessage.setAction(ChatActions.ACTION_LOGIN);
        return gson.toJson(chatMessage);
    }

    // 退出登录json
    public static String processLogout(String id){
        Gson gson = new Gson();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(id);
        chatMessage.setAction(ChatActions.ACTION_LOGOUT);
        return gson.toJson(chatMessage);
    }
}
