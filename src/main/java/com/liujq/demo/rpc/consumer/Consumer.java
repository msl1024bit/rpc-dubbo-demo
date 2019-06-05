package com.liujq.demo.rpc.consumer;

import com.liujq.demo.rpc.framework.ProxyFactory;
import com.liujq.demo.rpc.provider.api.HelloService;

/**
 * 消费者
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class Consumer {
    public static void main(String[] args) {

        // 得到代理接口类
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);

        // 执行实现类
        String result = helloService.sayHello("123123");
        System.out.println(result);
    }
}
