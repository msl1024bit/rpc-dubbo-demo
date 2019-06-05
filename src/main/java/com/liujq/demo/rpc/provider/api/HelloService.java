package com.liujq.demo.rpc.provider.api;

/**
 * 暴露给消费者用于远程调用的api接口
 *
 * @author Jiqiang.Liu
 * @date 2019-05-28
 */
public interface HelloService {

    String sayHello(String name);
}
