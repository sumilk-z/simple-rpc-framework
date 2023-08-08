package org.example.provider;

import lombok.extern.slf4j.Slf4j;
import org.example.annotation.RpcService;
import org.example.common.ServiceMeta;
import org.example.config.RpcProperties;
import org.example.register.RegistryFactory;
import org.example.register.RegistryService;
import org.example.utils.RpcServiceNameBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName providerPostProcessor
 * @Description
 * @Author zhucui
 * @Date 2023/8/7 11:12
 **/
@Slf4j
public class ProviderPostProcessor implements BeanPostProcessor, EnvironmentAware {
    /**
     * 属性，在serEnvironment中读取配置文件
     */
    RpcProperties rpcProperties;
    /**
     * 服务提供方的主机的IP地址
     */
    private static String serverAddress;

    static {
        try {
            serverAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 本地缓存
     */
    private final Map<String, Object> rpcServiceCache = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 有这个注解，说明这是RPC服务提供方，需要将其进行注册
        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        if (rpcService != null) {
            // 找到实现的接口名，默认是第一个； 也可以特殊指定
            String serviceName = bean.getClass().getInterfaces()[0].getName();
            if (!rpcService.interfaceClass().equals(void.class)) {
                serviceName = rpcService.interfaceClass().getName();
            }
            String version = rpcService.version();
            String group = rpcService.group();
            try {
                ServiceMeta serviceMeta = new ServiceMeta();
                serviceMeta.setServiceName(serviceName);
                serviceMeta.setServiceVersion(rpcService.version());
                serviceMeta.setServiceGroup(rpcService.group());
                serviceMeta.setServiceAddr(serverAddress);
                serviceMeta.setServicePort(rpcProperties.getPort());
                // 获取注册中心，并注册服务
                RegistryService registryService = RegistryFactory.getRegistryService(rpcProperties.getRegisterType());
                registryService.registry(serviceMeta);
                // 缓存到本地
                rpcServiceCache.put(RpcServiceNameBuilder.buildServiceKey(group, serviceName, version), bean);
                log.info("register server {} version {} group {}", serviceName, version, group);
            } catch (Exception e) {
                log.error("fail to register server {}, version {}, group {}", serviceName, version, group);
                e.printStackTrace();
            }
        }
        return bean;
    }

    /**
     * 读取provider的配置文件
     *
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        String prefix = "rpc.";
        Integer port = Integer.parseInt(Objects.requireNonNull(environment.getProperty(prefix + "port")));
        String registerAddr = environment.getProperty(prefix + "register-address");
        String registerPsw = environment.getProperty(prefix + "register-password");
        String serialization = environment.getProperty(prefix + "serialization");
        String registerType = environment.getProperty(prefix + "register-type");
        RpcProperties properties = RpcProperties.getInstance();
        properties.setPort(port);
        properties.setRegisterAddr(registerAddr);
        properties.setRegisterPsw(registerPsw);
        properties.setSerialization(serialization);
        properties.setRegisterType(registerType);
        rpcProperties = properties;
        log.info("成功读取provider的配置文件");
        log.info("【注册中心类型】 {}", registerType);
        log.info("【注册中心地址】 {}", registerAddr);
        log.info("【序列化方式】 {}", serialization == null ? "json" : serialization);
    }
}
