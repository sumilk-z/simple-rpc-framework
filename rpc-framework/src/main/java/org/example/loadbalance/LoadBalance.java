package org.example.loadbalance;

import org.example.common.URL;

import java.util.List;
import java.util.Random;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/3 23:24
 ***/
public class LoadBalance {
    /**
     * 随机策略
     * @param list
     * @return
     */
    public static URL random(List<URL> list) {
        Random random = new Random(list.size());
        return list.get(random.nextInt());
    }

    /**
     * 负载均衡
     * @param list
     * @return
     */
    public static URL balance(List<URL> list) {
        return list.get(0);
    }
}
