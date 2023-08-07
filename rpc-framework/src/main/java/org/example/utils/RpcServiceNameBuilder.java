package org.example.utils;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/7 21:17
 ***/
public class RpcServiceNameBuilder {
    public static String buildServiceKey(String group, String serviceName, String version) {
        return String.join(":", group, serviceName, version);
    }
}
