package org.example.ptotocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.example.common.Invocation;
import org.example.register.LocalRegister;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/3 21:11
 ***/
public class HttpServerHandler {
    public void handler(HttpServletRequest req, HttpServletResponse resp) {
        // 处理请求——》 接口，方法，参数
        try {
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            String interfaceName = invocation.getInterfaceName();
            // 根据接口名称找到对应的实现类：需要通过注册中心
            Class implClass = LocalRegister.get(interfaceName, invocation.getVersion());
            // 根据实现类获取需要执行的方法
            try {
                Method method = implClass.getDeclaredMethod(invocation.getMethodName(), invocation.getParameterTypes());
                // 通过反射执行方法
                Object result = method.invoke(implClass.newInstance(), invocation.getParameter());
                // 将结果写到response的OutputStream流中, 适用Jackson进行序列化
                ObjectMapper objectMapper = new ObjectMapper();
                byte[] bytes = objectMapper.writeValueAsBytes(result);
                IOUtils.write(bytes, resp.getOutputStream());
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
