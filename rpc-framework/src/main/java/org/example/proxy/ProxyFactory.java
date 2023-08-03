package org.example.proxy;

import org.example.common.Invocation;
import org.example.common.URL;
import org.example.loadbalance.LoadBalance;
import org.example.ptotocol.HttpClient;
import org.example.register.MapRemoteRegister;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
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

            // 服务调用
            String result = null;
            // 重试机制
            int max = 3;
            List<URL> used = new ArrayList<>();
            while (max-- > 0) {
                try {
                    URL host = LoadBalance.random(urls);
                    urls.remove(host); // 用过的下次不能再用了
                    result = httpClient.send(host.getHostname(), host.getPort(), invocation);
                } catch (Exception e) {
                    if (max > 0) {
                        continue;
                    }
                    return "出错了";
                }
            }
            return result;
        });
        return (T) proxyInstance;
    }
}
