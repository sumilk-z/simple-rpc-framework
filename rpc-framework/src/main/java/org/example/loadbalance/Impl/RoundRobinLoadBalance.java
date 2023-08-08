package org.example.loadbalance.Impl;

import org.example.common.ServiceMeta;
import org.example.loadbalance.AbstractLoadBalanceService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName RoundRobinLoadBalance
 * @Description
 * @Author zhucui
 * @Date 2023/8/8 14:54
 **/
public class RoundRobinLoadBalance extends AbstractLoadBalanceService {
    // 线程安全的自增轮询id
    private static AtomicInteger roundRobinId = new AtomicInteger(0);
    @Override
    protected ServiceMeta doSelect(List<ServiceMeta> serviceMetaList, Object[] params) {
        roundRobinId.addAndGet(1);
        if (roundRobinId.get() == Integer.MAX_VALUE) {
            roundRobinId.set(0);
        }
        return serviceMetaList.get(roundRobinId.get() % serviceMetaList.size());
    }
}
