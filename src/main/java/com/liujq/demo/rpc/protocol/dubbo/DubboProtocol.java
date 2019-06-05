package com.liujq.demo.rpc.protocol.dubbo;

import com.liujq.demo.rpc.framework.Request;
import com.liujq.demo.rpc.framework.Protocol;
import com.liujq.demo.rpc.framework.URL;

/**
 * dubbo通信协议 基于netty实现
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class DubboProtocol implements Protocol {
    @Override
    public void start(URL url) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHostName(), url.getPort());
    }

    @Override
    public Object send(URL url, Request request) {
        NettyClient nettyClient = new NettyClient();
        return nettyClient.send(url.getHostName(), url.getPort(), request);
    }
}
