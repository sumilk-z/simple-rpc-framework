package org.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.utils.RpcServiceNameBuilder;

import java.io.Serializable;

/**
 * @ClassName RpcRequest
 * @Description
 * @Author zhucui
 * @Date 2023/8/8 13:43
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    /**
     * 请求id
     */
    private String requestId;
    /**
     * 所在分组
     */
    private String group;
    /**
     * 调用的服务版本
     */
    private String version;
    /**
     * 调用的接口名
     */
    private String serviceName;
    /**
     * 调用的方法名
     */
    private String methodName;

    /**
     * 参数列表
     */
    private Object[] params;

    /**
     * 参数类型列表
     */
    private Class<?>[] parameterTypes;

    /**
     * 构建rpc服务的key
     * @return
     */
    public String getRpcServiceName() {
        return RpcServiceNameBuilder.buildServiceKey(group, serviceName, version);
    }
}
