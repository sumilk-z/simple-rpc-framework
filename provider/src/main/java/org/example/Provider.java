package org.example;

import org.example.common.URL;
import org.example.ptotocol.HttpServer;
import org.example.register.LocalRegister;
import org.example.register.MapRemoteRegister;
import org.example.service.HelloService;
import org.example.service.impl.HelloServiceImpl;
import org.example.service.impl.HelloServiceImpl2;



/***
 * @Description 原始的方法：需要自己手动注册服务，比较麻烦
 * @Author zhucui
 * @DateTime 2023/8/3 20:47
 ***/
public class Provider {
    public static void main(String[] args) {
        // 本地注册服务：注册实现类
        LocalRegister.register(HelloService.class.getName(), "1.0", HelloServiceImpl.class);
        LocalRegister.register(HelloService.class.getName(), "2.0", HelloServiceImpl2.class);

        // 远程注册服务：注册节点
        URL url = new URL("localhost", 8080);
        MapRemoteRegister.register(HelloService.class.getName(), url);

        // 启动http服务器
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostname(), url.getPort());
    }
}
