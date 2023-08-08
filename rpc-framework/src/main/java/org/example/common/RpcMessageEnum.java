package org.example.common;

/**
 * @ClassName RpcMessageEnum
 * @Description
 * @Author zhucui
 * @Date 2023/8/8 14:10
 **/
public enum RpcMessageEnum {
    /**
     * 请求消息
     */
    REQUEST,
    /**
     * 响应消息
     */
    RESPONSE,
    /**
     * 心跳测试连接消息
     */
    HEART_BEAT;
}
