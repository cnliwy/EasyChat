package com.liwy.easychat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liwy on 2017/5/16.
 */

public class ServerManager implements Runnable{
    public static final int PORT = 8888;
    private ServerSocket server;
    public static int count = 0;
    private static Map<Integer,ClientThread> clients;
    private static ServerManager instance;

    public static void init(int port){
        instance = new ServerManager(8888);
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
            clients = new HashMap<Integer, ClientThread>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ServerManager() {
        try {
            this.server = new ServerSocket(PORT);
            clients = new HashMap<Integer, ClientThread>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                Socket socket = server.accept();
                System.out.println("接收到来自客户端" + count + "的连接");
                ClientThread clientThread = null;
                if (count == 0){
                    clientThread = new ClientThread(socket,0,1);
                }else if (count == 1){
                    clientThread = new ClientThread(socket,1,0);
                }else{
                    clientThread = new ClientThread(socket,count,count+1);
                }
                new Thread(clientThread).start();
                clients.put(count++, clientThread);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 回复消息给某人
    public static void sendTo(int userId,String content){
        ClientThread clientThread = clients.get(userId);
        clientThread.send(content);
    }

    // 离线
    public static void offline(int userId){
        clients.remove(userId);
    }

    public static void main(String[] args) {
//        ServerSocket serverSocket;
//        try {
//            serverSocket = new ServerSocket(8888);
//            Socket socket = serverSocket.accept();
//            System.out.println("接收到来自客户端的连接！");
//            InputStream inputStream = socket.getInputStream();
//            byte[] buffer = new byte[1024 * 4];
//            boolean flag = true;
//            while (flag){
//                int temp = inputStream.read(buffer);
//                if (temp != -1){
//                    String content = new String(buffer,0,temp);
//                    if ("0".equals(content))flag = false;
//                    System.out.println("客户端说：" + content);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            System.out.println("服务器关闭");
//        }
        ServerManager.init(8888);//开启服务端
    }
}
