package org.example.config;

import org.apache.commons.lang.StringUtils;
import org.example.common.constant.SerializationType;

import java.util.HashMap;
import java.util.Map;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/7 20:09
 ***/
public class RpcProperties {
    /**
     * netty 端口
     */
    private int port;

    /**
     * 注册中心地址,例如redis服务的地址，格式为： IP:PORT
     */
    private String registerAddr;

    /**
     * 注册中心类型
     */
    private String registerType;

    /**
     * 注册中心密码
     */
    private String registerPsw;

    /**
     * 序列化
     */
    private String serialization;

    /**
     * 服务端额外配置数据
     */
    private Map<String, Object> serviceAttachments = new HashMap<>();

    /**
     * 客户端额外配置数据
     */
    private Map<String, Object> clientAttachments = new HashMap<>();

    /**
     * 单例对象
     */
    static RpcProperties rpcProperties;

    public static RpcProperties getInstance() {
        if (rpcProperties == null) {
            rpcProperties = new RpcProperties();
        }
        return rpcProperties;
    }

    /**
     * 禁止使用构造函数
     */
    private RpcProperties() {

    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRegisterAddr() {
        return registerAddr;
    }

    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getRegisterPsw() {
        return registerPsw;
    }


    public void setRegisterPsw(String registerPsw) {
        this.registerPsw = registerPsw;
    }

    public String getSerialization() {
        return serialization;
    }

    /**
     * 默认序列化方式为json
     *
     * @param serialization
     */
    public void setSerialization(String serialization) {
        if (StringUtils.isBlank(serialization)) {
            serialization = SerializationType.JSON;
        }
        this.serialization = serialization;
    }

    public Map<String, Object> getServiceAttachments() {
        return serviceAttachments;
    }

    public void setServiceAttachments(Map<String, Object> serviceAttachments) {
        this.serviceAttachments = serviceAttachments;
    }

    public Map<String, Object> getClientAttachments() {
        return clientAttachments;
    }

    public void setClientAttachments(Map<String, Object> clientAttachments) {
        this.clientAttachments = clientAttachments;
    }
}
