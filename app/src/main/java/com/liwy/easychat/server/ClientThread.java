package com.liwy.easychat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by liwy on 2017/5/18.
 */

public class ClientThread implements Runnable{
    // 客户端的socket连接
    private Socket socket;
    // 跟谁聊天
    private int talkTo;
    private int self;

    private OutputStream outputStream;
    private InputStream inputStream;

    public ClientThread(Socket socket, int self, int talkTo) {
        this.socket = socket;
        this.talkTo = talkTo;
        this.self = self;
    }

    @Override
    public void run() {
            try {
                inputStream = socket.getInputStream();
                byte[] buffer = new byte[1024 * 4];
                boolean flag = true;
                while (flag){
                    int temp = inputStream.read(buffer);
                    if (temp != -1){
                        String content = new String(buffer,0,temp);
                        if ("0".equals(content))flag = false;
                        System.out.println("客户端" + self + "说：" + content);
                        // 转发消息
                        if (self == 1){
                            ServerManager.sendTo(0,content);
                        }
                        if (self == 0){
                            ServerManager.sendTo(1,content);
                        }
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
                    ServerManager.offline(self);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

    public int getTalkTo() {
        return talkTo;
    }

    public void setTalkTo(int talkTo) {
        this.talkTo = talkTo;
    }

    public int getSelf() {
        return self;
    }

    public void setSelf(int self) {
        this.self = self;
    }
}
