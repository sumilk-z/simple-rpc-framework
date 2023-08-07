package org.example.service.impl;

import org.example.annotation.RpcService;
import org.example.common.Message;
import org.example.service.BusinessService;

/**
 * @ClassName BusinessServiceImpl
 * @Description
 * @Author zhucui
 * @Date 2023/8/7 10:55
 **/
@RpcService(group = "business-service", version = "version1")
public class BusinessServiceImpl implements BusinessService {
    @Override
    public String doBusiness(Message message) {
        return message.toString();
    }
}
