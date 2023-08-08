package org.example.loadbalance;

import org.example.extension.ExtensionLoader;

/**
 * @ClassName LoadBalanceFactory
 * @Description
 * @Author zhucui
 * @Date 2023/8/8 15:54
 **/
public class LoadBalanceFactory {
    public static LoadBalanceService get(String loadBalanceType) {
        return ExtensionLoader.getExtensionLoader(LoadBalanceService.class).getExtension(loadBalanceType);
    }
}
