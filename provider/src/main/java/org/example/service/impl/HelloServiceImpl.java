package org.example.service.impl;

import org.example.annotation.RpcService;
import org.example.common.Message;
import org.example.service.HelloService;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/3 20:37
 ***/
@RpcService(group = "hello-service", version = "version1")
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(Message message) {
        return message.toString();
    }
}
