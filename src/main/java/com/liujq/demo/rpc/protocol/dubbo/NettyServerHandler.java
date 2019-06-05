package com.liujq.demo.rpc.protocol.dubbo;

import com.liujq.demo.rpc.framework.Request;
import com.liujq.demo.rpc.framework.Response;
import com.liujq.demo.rpc.framework.URL;
import com.liujq.demo.rpc.register.Register;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;

/**
 * Netty服务端处理器
 *
 * @author Jiqiang.Liu
 * @date 2019-05-28
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 得到传输过来的数据和本机的域名和端口
        Request request = (Request) msg;
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().localAddress();

        // 根据URL和接口名去注册中心获取对应的实现类
        Class serviceImpl = Register.get(new URL(inetSocketAddress.getHostName(), inetSocketAddress.getPort()), request.getInterfaceName());

        // 执行实现类
        Method method = serviceImpl.getMethod(request.getMethodName(), request.getParamTypes());

        // 得到执行结果
        Object result = method.invoke(serviceImpl.newInstance(), request.getParams());

        System.out.println("Netty-------" + result.toString());

        // 返回结果
        ctx.writeAndFlush(new Response<>(request.getId(), result));
    }
}
