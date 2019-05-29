package com.liujq.demo.rpc.provider;

import com.liujq.demo.rpc.client.HelloService;

/**
 * @author Jiqiang.Liu
 * @date 2019-05-28
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        return "msg=" +  msg;
    }
}
