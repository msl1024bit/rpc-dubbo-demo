package com.liujq.demo.rpc.netty;

import com.liujq.demo.rpc.consumer.ClientBootstrap;
import com.liujq.demo.rpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.util.StringUtils;

/**
 * @author Jiqiang.Liu
 * @date 2019-05-28
 */
public class HelloServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read data... msg = " + msg);
        // 接收参数
        // 简单判断是否是PROVIDER_NAME开头，如果符合的话就新建HelloService的实现类去执行方法
        if (StringUtils.startsWithIgnoreCase(msg.toString(), ClientBootstrap.PROVIDER_NAME)) {
            String result = new HelloServiceImpl().sayHello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("channel active...");
    }
}
