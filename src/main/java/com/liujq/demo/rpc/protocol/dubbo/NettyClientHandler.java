package com.liujq.demo.rpc.protocol.dubbo;

import com.liujq.demo.rpc.framework.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 消费者客户端handler
 *
 * @author Jiqiang.Liu
 * @date 2019-05-28
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 收到服务端数据 写入future的response
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Response res = (Response) msg;
        DefaultFuture.writeResponse(res);
    }
}
