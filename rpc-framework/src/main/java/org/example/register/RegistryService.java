package org.example.register;

import org.example.common.ServiceMeta;

import java.util.List;

/***
 * @Description 服务注册中心接口
 * @Author zhucui
 * @DateTime 2023/8/7 21:24
 ***/
public interface RegistryService {
    /**
     * 服务注册
     *
     * @param serviceMeta
     */
    void registry(ServiceMeta serviceMeta);

    /**
     * 取消服务注册
     *
     * @param serviceMeta
     */
    void unRegistry(ServiceMeta serviceMeta);

    /**
     * 销毁服务
     */
    void destroy();

    /**
     * 获取服务列表
     *
     * @param serviceName
     * @return
     */
    List<ServiceMeta> getService(String serviceName);
}
