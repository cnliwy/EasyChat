package com.liwy.easychat.chat;

import com.google.gson.Gson;
import com.liwy.easychat.entity.ChatMessage;
import com.liwy.easychat.entity.DataMessage;

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

    private LoginListener loginListener;
    private NewMessageListener messageListener;
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
    public void connectServer(final String host, final int port){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new Socket(host,port);
                    try {
                        InputStream inputStream = clientSocket.getInputStream();
                        byte[] buffer = new byte[1024 * 4];
                        boolean flag = true;
                        while (flag){
                            int temp = inputStream.read(buffer);
                            if (temp != -1){
                                String content = new String(buffer,0,temp);
//                                System.out.println("客户端" + 0 + "说：" + content);
                                Gson gson = new Gson();
                                DataMessage dataMessage = gson.fromJson(content,DataMessage.class);
                                System.out.println("类型：" + dataMessage.getType() + ",内容：" + dataMessage.getContent());
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
    OutputStream outputStream = null;
    // 发送数据至服务器
    public void sendDataToServer(final String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    outputStream = clientSocket.getOutputStream();
                    outputStream.write(content.getBytes());
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void login(String id){
        String content = "";

    }
    // 发送文字消息
    public void sendMessage(String text){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(id);
        chatMessage.setTalkId(talkId);
        chatMessage.setContent(text);
        chatMessage.setContentType(ChatMessage.CONTENT_TYPE_CHAT);
        chatMessage.setType(ChatMessage.TYPE_TEXT);
        chatMessage.setDataType(ChatMessage.DATA_TYPE_MESSAGE);
        String content = MessageUtil.processMessage(chatMessage);
        sendDataToServer(content);
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

    public LoginListener getLoginListener() {
        return loginListener;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public NewMessageListener getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(NewMessageListener messageListener) {
        this.messageListener = messageListener;
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

}
