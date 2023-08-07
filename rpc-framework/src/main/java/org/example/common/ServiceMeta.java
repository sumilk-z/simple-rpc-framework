package org.example.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/***
 * @Description 服务元数据，传输用的？
 * @Author zhucui
 * @DateTime 2023/8/7 21:03
 ***/
@Data
public class ServiceMeta implements Serializable {
    /**
     * 服务名称：接口名
     */
    private String serviceName;

    /**
     * 服务版本号，例如相同的实现类有不同的更新迭代版本
     */
    private String serviceVersion;

    /**
     * 服务分组：例如同一个接口有不同的实现类，那么就可以放到不同的组别中
     */
    private String serviceGroup;

    /**
     * 服务提供方的IP地址, 服务注册的时候自动读取本机IP地址
     */
    private String serviceAddr;

    /**
     * 服务提供方的端口号，从配置文件中读取
     */
    private int servicePort;

    /**
     * 用于redis中检测服务是否到期
     */
    private long endTime;

    /**
     * 用于判断是否是特殊服务：例如注册中心的服务即便到期也不能挂掉
     */
    private String UUID;

    /**
     * 故障转移需要移除不可用服务, 重写equals方法
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceMeta that = (ServiceMeta) o;
        return servicePort == that.servicePort &&
                Objects.equals(serviceName, that.serviceName) &&
                Objects.equals(serviceVersion, that.serviceVersion) &&
                Objects.equals(serviceGroup, that.serviceGroup) &&
                Objects.equals(serviceAddr, that.serviceAddr) &&
                Objects.equals(UUID, that.UUID);
    }

    /**
     * 需要重写hashCode方法
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(serviceName, serviceVersion, serviceGroup, serviceAddr, servicePort, UUID);
    }
}
