package org.example.common.constant;

/**
 * @ClassName LoadBalanceType
 * @Description
 * @Author zhucui
 * @Date 2023/8/8 15:59
 **/
public class LoadBalanceType {
    /**
     * 随机算法
     */
    public static final String RANDOM = "random";
    /**
     * 一致性hash算法
     */
    public static final String CONSISTENT_HASH = "consistentHash";

    /**
     * 轮询算法
     */
    public static final String ROUND_ROBIN = "roundRobin";
}
