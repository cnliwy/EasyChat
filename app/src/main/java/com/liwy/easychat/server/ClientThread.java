package com.liwy.easychat.server;

import com.google.gson.Gson;
import com.liwy.easychat.chat.ChatActions;
import com.liwy.easychat.chat.MessageUtil;
import com.liwy.easychat.entity.ChatMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import static android.R.id.message;
import static com.liwy.easychat.utils.DataParse.parseJson;

/**
 * Created by liwy on 2017/5/18.
 */

public class ClientThread implements Runnable{
    // 客户端的socket连接
    private Socket socket;
    // 跟谁聊天
    private String talkId;
    private String id;

    private OutputStream outputStream;
    private InputStream inputStream;



    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                byte[] buffer = new byte[1024 * 4];
                boolean flag = true;
                while (flag){
                    int temp = inputStream.read(buffer);
                    if (temp != -1){
                        String content = new String(buffer,0,temp);
                        ChatMessage chatMessage = parseJson(content);
                        parseMessage(chatMessage);
                    }
                }
            }catch (SocketException e){
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (inputStream != null)inputStream.close();
                    if (outputStream != null)outputStream.close();
                    if (socket != null || socket.isClosed())socket.close();
                    inputStream = null;
                    outputStream = null;
                    socket = null;
                    ServerManager.getInstance().offline(id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    /**
     * 解析ChatMessage数据
     * @param msg
     */
    public void parseMessage(ChatMessage msg){
        String action = msg.getAction();
        if (action != null && !"".equals(action)){
            if (ChatActions.ACTION_LOGIN.equals(action)){
                this.id = msg.getId();
                ServerManager.getInstance().online(msg.getId(),this);
                msg.setContent("登录成功");
                msg.setMessageType(ChatMessage.MESSAGE_TYPE_SYSTEM);
                send(MessageUtil.processMessage(msg));
            }else if (ChatActions.ACTION_LOGOUT.equals(action)){
                ServerManager.getInstance().offline(msg.getId());
            }else if (ChatActions.ACTION_SEND.equals(action)){
                msg.setId(msg.getTalkId());
                msg.setTalkId(id);
                msg.setDirection(ChatMessage.DIRECTION_RECEIVE);
                ServerManager.talkTo(msg);
            }else if (ChatActions.ACTION_USERS.equals(action)){
                getUsers(msg.getId(),action);
            }
        }
    }
    /**
     * 登录、退出操作
     */
    public void login(){}

    public void logout(){}

    /**
     * 获取好友列表
     */
    public void getUsers(String id,String action){
        ChatMessage msg = new ChatMessage();
        msg.setId(id);
        String users =  ServerManager.getInstance().getUsers(id);
        msg.setContent(users);
        msg.setMessageType(ChatMessage.MESSAGE_TYPE_ROSTER);
        msg.setAction(action);
        send(MessageUtil.processMessage(msg));
    }
    // 发送数据
    public void send(String content){
        try {
            if (outputStream == null)outputStream = socket.getOutputStream();
            outputStream.write(content.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getTalkId() {
        return talkId;
    }

    public void setTalkId(String talkId) {
        this.talkId = talkId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
