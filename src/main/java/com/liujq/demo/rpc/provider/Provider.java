package com.liujq.demo.rpc.provider;

import com.liujq.demo.rpc.framework.Protocol;
import com.liujq.demo.rpc.framework.ProtocolFactory;
import com.liujq.demo.rpc.framework.URL;
import com.liujq.demo.rpc.provider.api.HelloService;
import com.liujq.demo.rpc.provider.impl.HelloServiceImpl;
import com.liujq.demo.rpc.register.Register;

/**
 * 服务提供者
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class Provider {
    public static void main(String[] args) {

        // 注册服务
        URL url = new URL("localhost", 8080);
        Register.register(HelloService.class.getName(), url, HelloServiceImpl.class);

        // 启动服务
        Protocol protocol = ProtocolFactory.getProtocol();
        protocol.start(url);
    }
}
