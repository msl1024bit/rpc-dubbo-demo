package com.liujq.demo.rpc.consumer;

import com.liujq.demo.rpc.client.HelloService;
import com.liujq.demo.rpc.netty.NettyClient;

/**
 * @author Jiqiang.Liu
 * @date 2019-05-28
 */
public class ClientBootstrap {

    public static final String PROVIDER_NAME = "HelloService#sayHello#";

    public static void main(String[] args) throws InterruptedException {

        // 启动
        NettyClient consumer = new NettyClient();
        consumer.start("localhost", 8080);

        // 创建一个代理对象
        HelloService service = consumer.getProxy(HelloService.class, PROVIDER_NAME);

        for (; ; ) {
            Thread.sleep(1000);
            System.out.println(service.sayHello("are you ok ?"));
        }
//        System.out.println(service.sayHello("are you ok ?"));
    }

}
