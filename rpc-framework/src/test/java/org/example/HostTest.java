package org.example;

import java.net.InetAddress;
import java.net.UnknownHostException;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/7 20:25
 ***/
public class HostTest {
    public static void main(String[] args) throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        System.out.println(hostAddress);
    }
}
