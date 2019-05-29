package com.liujq.demo.rpc.provider;

import com.liujq.demo.rpc.netty.NettyServer;

/**
 * @author Jiqiang.Liu
 * @date 2019-05-28
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer(8080);
    }
}
