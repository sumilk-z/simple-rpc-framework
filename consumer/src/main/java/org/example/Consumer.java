package org.example;

import org.example.proxy.ProxyFactory;
import org.example.service.HelloService;

import java.io.IOException;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/3 20:38
 ***/
public class Consumer {
    public static void main(String[] args) throws IOException {
        // 0.期望的调用方式
//        HelloService helloService = ? // 由服务提供者来提供实现类
//        String result = helloService.hello("world");


        // 1.最开始的适用方式
//        Invocation invocation = new Invocation(
//                HelloService.class.getName(),
//                "1.0",
//                "hello",
//                new Class[]{String.class},
//                new Object[]{"world"}
//        );
//        // 通过网路发送这个Invocation对象
//        HttpClient httpClient = new HttpClient();
//        String result = httpClient.send("localhost", 8080, invocation);
//        System.out.println(result);

        // 不希望用户自己构造Invocation对象，可以通过Jdk动态代理生成代理对象的模式
        HelloService helloService = ProxyFactory.getProxy(HelloService.class, "2.0");
        String result = helloService.hello("world");
        System.out.println(result);

    }
}
