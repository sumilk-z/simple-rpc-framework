package org.example.register;

import org.example.common.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @Description 远程的注册中心，结构为<String, List<URL>> 即接口名称，集群中的多个节点提供服务
 * 这个注册中心是在provider这里的，consumer那边用不到，因为是两个进程，所以服务注册中心需要是第三方，常见的例如redis， zookeeper
 * 服务注册中心还需要实现： 心跳机制，检测服务是否依旧有效
 * 此外：还需要设计本地缓存，否则每次调用服务都去服务注册中心查找效率很低
 * @Author zhucui
 * @DateTime 2023/8/3 23:13
 ***/
public class MapRemoteRegister {
    private static Map<String, List<URL>> map = new HashMap<>();

    public static void register(String interfaceName, URL url) {
        List<URL> list = map.get(interfaceName);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(url);
        map.put(interfaceName, list);
    }
    public static List<URL> get(String interfaceName) {
        return map.get(interfaceName);
    }
}
