package org.example.proxy;

import org.example.common.Invocation;
import org.example.common.URL;
import org.example.loadbalance.LoadBalance;
import org.example.ptotocol.HttpClient;
import org.example.register.MapRemoteRegister;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.List;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/3 22:57
 ***/
public class ProxyFactory {
    public static <T> T getProxy(Class interfaceClass, String version) throws IOException {
        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, (proxy, method, args) -> {
            Invocation invocation = new Invocation(interfaceClass.getName(), version, method.getName(), method.getParameterTypes(), args);
            HttpClient httpClient = new HttpClient();
            // 服务发现
            List<URL> urls = MapRemoteRegister.get(interfaceClass.getName());
            // 负载均衡
            URL host = LoadBalance.random(urls);
            // 服务调用
            String result = null;
            try {
                result = httpClient.send(host.getHostname(), host.getPort(), invocation);
            } catch (Exception e) {
                // 服务容错，这里可以调用回调函数
                return "报错了";
            }
            return result;
        });
        return (T) proxyInstance;
    }
}
