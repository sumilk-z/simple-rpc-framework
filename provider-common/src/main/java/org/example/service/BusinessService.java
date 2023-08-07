package org.example.service;

import org.example.common.Message;

/**
 * @ClassName BusinessService
 * @Description
 * @Author zhucui
 * @Date 2023/8/7 10:46
 **/
public interface BusinessService {
    /**
     * 执行业务
     * @param message
     * @return
     */
    String doBusiness(Message message);
}
