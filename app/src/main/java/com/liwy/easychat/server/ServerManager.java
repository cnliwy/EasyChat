package com.liwy.easychat.server;

import com.liwy.easychat.chat.ChatActions;
import com.liwy.easychat.chat.MessageUtil;
import com.liwy.easychat.entity.ChatMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static android.R.attr.key;

/**
 * Created by liwy on 2017/5/16.
 */

public class ServerManager implements Runnable{
    public static final int PORT = 8888;
    private ServerSocket server;
    // 用户连接线程缓存
    private static Map<String,ClientThread> clients;
    private static ServerManager instance;

    public static void init(int port){
        instance = new ServerManager(port);
        new Thread(instance).start();
    }

    public static ServerManager getInstance() {
        return instance;
    }

    // 构造方法
    private ServerManager(int port) {
        try {
            if (port == 0){
                this.server = new ServerSocket(PORT);
            }else{
                this.server = new ServerSocket(port);
            }
            clients = new HashMap<String, ClientThread>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ServerManager() {
        try {
            this.server = new ServerSocket(PORT);
            clients = new HashMap<String, ClientThread>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                Socket socket = server.accept();
                System.out.println("接收到来自客户端的连接");
                ClientThread clientThread = null;
                clientThread = new ClientThread(socket);
                new Thread(clientThread).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取好友列表（不包括自己）
     * @return
     */
    public String getUsers(String userId){
        Set<String> keys = clients.keySet();
        StringBuffer sb = new StringBuffer();
        for (String key : keys){
            if (!userId.equals(key))sb.append(key).append(",");
        }
        if (sb.length() > 0)sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
    // 上线，通知好友更新列表
    public void online(String id,ClientThread client){
        clients.put(id,client);
        pushRosterNotification(id);//有人上线后更新好友列表
    }


    // 离线，清除缓存，通知好友更新列表
    public void offline(String userId){
        System.out.println(userId + "下线了");
        clients.remove(userId);
        pushRosterNotification(userId);
    }

    /**
     * 推送好友列表
     * @param id
     */
    public void pushRosterNotification(String id){
        Set<String> keys = clients.keySet();
        for (String key : keys){
            if (!id.equals(key)){
                ClientThread client = clients.get(key);
                client.getUsers(key, ChatActions.ACTION_USER_UPDATE);
            }
        }
    }


    // 回复消息给某人
    public static void talkTo(ChatMessage msg){
        ClientThread clientThread = clients.get(msg.getId());
        clientThread.send(MessageUtil.processMessage(msg));
    }

    public static void main(String[] args) {
        ServerManager.init(8888);//开启服务端
    }
}
