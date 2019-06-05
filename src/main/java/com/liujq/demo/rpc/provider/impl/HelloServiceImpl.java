package com.liujq.demo.rpc.provider.impl;

import com.liujq.demo.rpc.provider.api.HelloService;

/**
 * @author Jiqiang.Liu
 * @date 2019-05-28
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello" + name;
    }
}
