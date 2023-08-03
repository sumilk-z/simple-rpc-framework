package org.example.register;

import java.util.HashMap;
import java.util.Map;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/3 21:23
 ***/
public class LocalRegister {
    private static Map<String, Class> map = new HashMap<>();

    /**
     * 注册服务
     * @param interfaceName 接口名称
     * @param version 版本号，可能有实现类有多种版本的情况
     * @param implClass 实现类
     */
    public static void register(String interfaceName, String version, Class implClass) {
        map.put(interfaceName + version, implClass);
    }

    public static Class get(String interfaceName, String version) {
        // 如果没有版本号，给定默认的实现类
        if (version == null || version.isEmpty()) {
            return map.get(interfaceName + "1.0");
        }
        return map.get(interfaceName + version);
    }
}
