package org.example.ptotocol;

import org.apache.commons.io.IOUtils;
import org.example.common.Invocation;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/3 21:48
 ***/
public class HttpClient {
    public String send(String hostname, Integer port, Invocation invocation) {
        try {
            URL url = new URL("http", hostname, port, "/");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // 配置
            OutputStream outputStream = connection.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            // 发送对象
            objectOutputStream.writeObject(invocation);
            objectOutputStream.flush();
            objectOutputStream.close();
            // 阻塞式获取结果
            InputStream inputStream = connection.getInputStream();
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
