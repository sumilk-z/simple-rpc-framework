package org.example.ptotocol.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/4 21:03
 ***/
public class ServerSocketDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            System.out.println("等待连接...");
            // 阻塞方法
            Socket clientSocket = serverSocket.accept();
            System.out.println("有客户端发起连接了");
            handle(clientSocket);
        }
    }

    public static void handle(Socket socket) throws IOException {
        byte[] buffer = new byte[1024];
        // 接受客户端的数据，阻塞方法，没有数据可读时就阻塞等待
        int len = socket.getInputStream().read(buffer);
        if (len != -1) {
            System.out.println("接收到客户端的消息：" + new String(buffer, 0, len));
        }
        System.out.println("end");
    }
}
