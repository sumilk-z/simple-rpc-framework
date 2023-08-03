package org.example.service.impl;

import org.example.service.HelloService;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/3 20:37
 ***/
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        return "implement version1: hello" + msg;
    }
}
