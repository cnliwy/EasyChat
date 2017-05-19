package com.liwy.easychat.entity;

/**
 * Created by liwy on 2017/5/18.
 */

public class ChatMessage {
    //消息类型
    public static final int MESSAGE_TYPE_SYSTEM = 1;      //系统
    public static final int MESSAGE_TYPE_ROSTER = 2;     // 好友
    public static final int MESSAGE_TYPE_CHAT = 3;    // 消息
    //聊天消息类型
    public static final int CHAT_TYPE_CHAT = 1;      //单聊
    public static final int CHAT_TYPE_GROUP = 2;     // 群聊

    //消息内容类型
    public static final int TYPE_TEXT = 1;                 // 文字
    public static final int TYPE_IMAGE = 2;                 // 图片
    public static final int TYPE_AUDIO = 3;                 // 语音

    // 消息来源
    public static final int DIRECTION_RECEIVE = 1;                 // 接收的消息
    public static final int DIRECTION_SEND = 2;                 // 发出的消息


    private String msdId;//消息id
    private String id;     //自己的id
    private String talkId;  //对方的id
    private int direction;  //1 from 接收的消息 2 to 发出的消息
    private String content; //消息内容
    private String groupId; //群id
    private int chatType;//1 单聊 2 群聊 3 公告
    private int contentType;//1 文字、2 图片、3 语音
    private int messageType;//1 系统 2好友 3消息
    private String action;//操作

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTalkId() {
        return talkId;
    }

    public void setTalkId(String talkId) {
        this.talkId = talkId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMsdId() {
        return msdId;
    }

    public void setMsdId(String msdId) {
        this.msdId = msdId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
