package com.liwy.easychat.entity;

/**
 * Created by liwy on 2017/5/18.
 */

public class ChatMessage {
    //数据类型
    public static final int DATA_TYPE_CHAT = 1;      //系统
    public static final int DATA_TYPE_ROSTER = 2;     // 好友
    public static final int DATA_TYPE_MESSAGE = 3;    // 消息
    //消息类型
    public static final int CONTENT_TYPE_CHAT = 1;      //单聊
    public static final int CONTENT_TYPE_GROUP = 2;     // 群聊
    public static final int CONTENT_TYPE_SYSTEM = 3;    // 系统消息

    //消息内容类型
    public static final int TYPE_TEXT = 1;                 // 文字
    public static final int TYPE_IMAGE = 2;                 // 图片
    public static final int TYPE_AUDIO = 3;                 // 语音

    private String msdId;//消息id
    private String id;     //自己的id
    private String talkId;  //对方的id
    private String content; //消息内容
    private String groupId; //群id
    private int type;//1 单聊 2 群聊 3 公告
    private int contentType;//1 文字、2 图片、3 语音
    private int dataType;//1 系统 2好友 3消息

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
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
}
