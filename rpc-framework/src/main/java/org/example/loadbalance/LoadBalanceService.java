package org.example.loadbalance;

import org.example.annotation.SPI;
import org.example.common.RpcRequest;
import org.example.common.ServiceMeta;

/**
 * @ClassName LoadBalanceService
 * @Description
 * @Author zhucui
 * @Date 2023/8/8 13:32
 **/
@SPI
public interface LoadBalanceService {
    /**
     * 根据服务名称找到一个可用的服务即可
     * @param request
     * @return
     */
    ServiceMeta select(RpcRequest request);
}
