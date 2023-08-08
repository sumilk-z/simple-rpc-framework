package org.example.register.Impl;

import org.example.common.ServiceMeta;
import org.example.register.RegistryService;

import java.util.List;

/**
 * @ClassName ZookeeperRegistryServiceImpl
 * @Description
 * @Author zhucui
 * @Date 2023/8/8 9:18
 **/
public class ZookeeperRegistryServiceImpl implements RegistryService {
    @Override
    public void registry(ServiceMeta serviceMeta) {

    }

    @Override
    public void unRegistry(ServiceMeta serviceMeta) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public List<ServiceMeta> getService(String serviceName) {
        return null;
    }
}
