package org.example.loadbalance.Impl;

import org.example.common.ServiceMeta;
import org.example.loadbalance.AbstractLoadBalanceService;
import org.example.utils.RpcServiceNameBuilder;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName ConsistentHashLoadBalance
 * @Description
 * @Author zhucui
 * @Date 2023/8/8 14:53
 **/
public class ConsistentHashLoadBalance extends AbstractLoadBalanceService {
    /**
     * 虚拟节点的数量，防止hash环倾斜
     */
    private final static int VIRTUAL_NODE_SIZE = 10;

    @Override
    protected ServiceMeta doSelect(List<ServiceMeta> serviceMetaList, Object[] params) {
        // 1.构建hash环
        TreeMap<Integer, ServiceMeta> ring = new TreeMap<>();
        for (ServiceMeta serviceMeta : serviceMetaList) {
            String serviceName = RpcServiceNameBuilder.buildServiceKey(
                    serviceMeta.getServiceGroup(),
                    serviceMeta.getServiceName(),
                    serviceMeta.getServiceVersion());
            for (int i = 0; i < VIRTUAL_NODE_SIZE; i++) {
                ring.put((serviceName + i).hashCode(), serviceMeta);
            }
        }
        // 2.从环上获取距离本次请求(用第一个参数作为依据）最近的节点
        Map.Entry<Integer, ServiceMeta> entry = ring.ceilingEntry(params[0].hashCode());
        entry = entry == null ? ring.firstEntry() : entry;
        return entry.getValue();
    }
}
