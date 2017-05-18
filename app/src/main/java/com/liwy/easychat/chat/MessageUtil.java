package com.liwy.easychat.chat;

import com.liwy.easychat.entity.ChatMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liwy on 2017/5/18.
 */

public class MessageUtil {
    private static MessageUtil instance;

    public static MessageUtil getInstance(int id, int talkTo){
        if (instance == null){
            synchronized (MessageUtil.class){
                if (instance == null){
                    instance = new MessageUtil(id, talkTo);
                    return instance;
                }
                return instance;
            }
        }
        return instance;
    }

    public MessageUtil(int id, int talkTo) {
//        this.id = id;
//        this.talkId = talkTo;
    }

    public static String processMessage(ChatMessage chatMessage){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",chatMessage.getId());
            jsonObject.put("type",chatMessage.getType());
            jsonObject.put("contentType",chatMessage.getContentType());
            jsonObject.put("talkId",chatMessage.getTalkId());
            jsonObject.put("content",chatMessage.getContent());
            jsonObject.put("dataType",chatMessage.getDataType());
            jsonObject.put("action",ChatActions.ACTION_SEND);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 生成登录json
    public static String processLogin(String id){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("action",ChatActions.ACTION_LOGIN);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
