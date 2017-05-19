package com.liwy.easychat.chat;

import com.liwy.easychat.callback.RosterMessageListener;
import com.liwy.easychat.callback.SystemMessageListener;
import com.liwy.easychat.callback.ChatMessageListener;
import com.liwy.easychat.entity.ChatMessage;
import com.liwy.easychat.utils.DataParse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by liwy on 2017/5/16.
 */

public class ConnectManager {
    private Socket clientSocket;
    private static ConnectManager connectManager;
    private String id;
    private String talkId;

    private SystemMessageListener systemMessageListener;
    private ChatMessageListener chatMessageListener;
    private RosterMessageListener rosterMessageListener;
    /**
     * 获取ThreadHelper的实例
     * @return
     */
    public static ConnectManager getInstance(){
        if (connectManager == null){
            synchronized (ConnectManager.class){
                if (connectManager == null){
                    connectManager = new ConnectManager();
                }
                return connectManager;
            }
        }
        return connectManager;
    }

    /**
     * 连接服务端并发送数据
     * @param host
     * @param port
     */
    public void connectServer(final String host, final int port, final String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new Socket(host,port);
                    try {
                        InputStream inputStream = clientSocket.getInputStream();
                        outputStream = clientSocket.getOutputStream();
                        login(id);
                        byte[] buffer = new byte[1024 * 4];
                        boolean flag = true;
                        while (flag){
                            int temp = inputStream.read(buffer);
                            if (temp != -1){
                                String content = new String(buffer,0,temp);
                                System.out.println("客户端说：" + content);
//                                Gson gson = new Gson();
//                                DataMessage dataMessage = gson.fromJson(content,DataMessage.class);
//                                System.out.println("类型：" + dataMessage.getType() + ",内容：" + dataMessage.getContent());
                                ChatMessage chatMessage = DataParse.parseJson(content);
                                if (chatMessage != null) parseMessageType(chatMessage);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void parseMessageType(ChatMessage msg){
        if (msg.getMessageType() == ChatMessage.MESSAGE_TYPE_SYSTEM){
            parseSystemMessage(msg);
        }else if (msg.getMessageType() == ChatMessage.MESSAGE_TYPE_ROSTER){
            parseRosterMessage(msg);
        }else if (msg.getMessageType() == ChatMessage.MESSAGE_TYPE_CHAT){
            parseChatMessage(msg);
        }
    }
    public void parseSystemMessage(ChatMessage msg){
        if (systemMessageListener != null)systemMessageListener.loginCallback(msg);
    }
    public void parseRosterMessage(ChatMessage msg){
        if (rosterMessageListener != null)rosterMessageListener.rosters(msg);
    }
    public void parseChatMessage(ChatMessage msg){
        if (chatMessageListener != null)chatMessageListener.messageArrived(msg);
    }
    OutputStream outputStream = null;
    // 发送数据至服务器
    private void sendData(final String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (outputStream == null)outputStream = clientSocket.getOutputStream();
                    outputStream.write(content.getBytes());
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void login(String id){
        this.id = id;
        String content = MessageUtil.processLogin(id);
        sendData(content);
    }
    // 发送文字消息
    public void sendMessage(String text){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(id);
        chatMessage.setTalkId(talkId);
        chatMessage.setContent(text);
        chatMessage.setChatType(ChatMessage.CHAT_TYPE_CHAT);
        chatMessage.setContentType(ChatMessage.TYPE_TEXT);
        chatMessage.setMessageType(ChatMessage.MESSAGE_TYPE_CHAT);
        String content = MessageUtil.processMessage(chatMessage);
        sendData(content);
    }
    // 获取好友列表
    public void getUsers(){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(id);
        chatMessage.setMessageType(ChatMessage.MESSAGE_TYPE_ROSTER);
        chatMessage.setAction(ChatActions.ACTION_USERS);
        String content = MessageUtil.processMessage(chatMessage);
        sendData(content);
    }

    //  关闭连接
    public void disconnect(){
        try {
            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public SystemMessageListener getSystemMessageListener() {
        return systemMessageListener;
    }

    public void setSystemMessageListener(SystemMessageListener systemMessageListener) {
        this.systemMessageListener = systemMessageListener;
    }

    public ChatMessageListener getChatMessageListener() {
        return chatMessageListener;
    }

    public void setChatMessageListener(ChatMessageListener chatMessageListener) {
        this.chatMessageListener = chatMessageListener;
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.131.19",8888);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            byte[] buffer = new byte[1024 * 4];
            boolean flag = true;
            while (flag){
                int temp = inputStream.read(buffer);
                if (temp != -1){
                    String content = new String(buffer,0,temp);
                    if ("0".equals(content))flag = false;
                    System.out.println("android客户端1说：" + content);
                    outputStream.write("谢谢,我已经收到了你的消息".getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RosterMessageListener getRosterMessageListener() {
        return rosterMessageListener;
    }

    public void setRosterMessageListener(RosterMessageListener rosterMessageListener) {
        this.rosterMessageListener = rosterMessageListener;
    }
}
