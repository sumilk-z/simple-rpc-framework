package org.example.loadbalance;

import org.example.common.RpcRequest;
import org.example.common.ServiceMeta;
import org.example.config.RpcProperties;
import org.example.register.RegistryFactory;
import org.example.register.RegistryService;
import org.example.utils.RpcServiceNameBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ClassName AbstractLoadBalanceService
 * @Description 抽象类：可以先实现接口的一部分
 * @Author zhucui
 * @Date 2023/8/8 13:33
 **/
public abstract class AbstractLoadBalanceService implements LoadBalanceService {
    @Override
    public ServiceMeta select(RpcRequest request) {
        // 读取配置文件，得到注册中心是什么类型的
        RegistryService registry = RegistryFactory.getRegistryService(RpcProperties.getInstance().getRegisterType());
        // 从注册中心获取到可用的服务
        String serviceName = RpcServiceNameBuilder.buildServiceKey(
                request.getGroup(), request.getServiceName(), request.getVersion()
        );
        List<ServiceMeta> serviceList = registry.getService(serviceName);
        if (CollectionUtils.isEmpty(serviceList)) {
            return  null;
        }
        if (serviceList.size() == 1) {
            return serviceList.get(0);
        }
        return doSelect(serviceList, request.getParams());
    }

    /**
     * 需要通过请求的方法的参数来区分不同的请求，这样才能做出选择，否则每个请求都没有区别
     * @param serviceMetaList
     * @param params
     * @return
     */
    protected abstract ServiceMeta doSelect(List<ServiceMeta> serviceMetaList, Object[] params);
}
