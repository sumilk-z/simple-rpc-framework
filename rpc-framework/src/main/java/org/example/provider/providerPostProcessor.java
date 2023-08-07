package org.example.provider;

import org.example.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @ClassName providerPostProcessor
 * @Description
 * @Author zhucui
 * @Date 2023/8/7 11:12
 **/
public class providerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 有这个注解，说明这是RPC服务提供方，需要将其进行注册
        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        if (rpcService != null) {
            // 实现的接口
            String serviceName = bean.getClass().getInterfaces()[0].getName();
        }

    }
}
