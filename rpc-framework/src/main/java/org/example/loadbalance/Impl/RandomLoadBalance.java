package org.example.loadbalance.Impl;

import org.example.common.ServiceMeta;
import org.example.loadbalance.AbstractLoadBalanceService;

import java.util.List;
import java.util.Random;

/**
 * @ClassName RandomLoadBalance
 * @Description 随机策略
 * @Author zhucui
 * @Date 2023/8/8 14:52
 **/
public class RandomLoadBalance extends AbstractLoadBalanceService {
    @Override
    protected ServiceMeta doSelect(List<ServiceMeta> serviceMetaList, Object[] params) {
        Random random = new Random();
        return serviceMetaList.get(random.nextInt(serviceMetaList.size()));
    }
}
