package org.example.register;

import org.example.extension.ExtensionLoader;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/8 0:27
 ***/
public class RegistryFactory {
    /**
     * 返回一个实例化的注册中心
     * @param registryServiceType 注册中心类型： redis或者zookeeper
     * @return
     */
    public static RegistryService getRegistryService(String registryServiceType) {
        return ExtensionLoader.getExtensionLoader(RegistryService.class).getExtension(registryServiceType);
    }
}
