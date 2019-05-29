package com.liujq.demo.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jiqiang.Liu
 * @date 2019-05-28
 */
public class NettyClient {

    private static Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private static ExecutorService executor =  Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private EventLoopGroup group = new NioEventLoopGroup();
    private static HelloClientHandler handler = new HelloClientHandler();

    @SuppressWarnings("unchecked")
    public <T> T getProxy(final Class<T> serviceClass, final String providerName) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serviceClass}, new ClientProxy(providerName));
    }

    /**
     * 代理对象
     * 用于将数据写入ChannelHandlerContext
     */
    class ClientProxy implements InvocationHandler {

        private String providerName;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            handler.setPara(providerName + args[0]);
            return executor.submit(handler).get();
        }

        ClientProxy(String providerName) {
            this.providerName = providerName;
        }
    }

    /**
     * 启动netty客户端
     *
     * @param host 域名
     * @param port 端口
     */
    public void start(String host, int port) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(host, port)
                .handler(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(handler);
                    }
                });
        try {
            bootstrap.connect().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        future.addListener((ChannelFutureListener) future1 -> {
//            if (future1.isSuccess()) {
//                logger.info("连接Netty服务端成功");
//            } else {
//                logger.info("连接失败，进行断线重连");
//                future1.channel().eventLoop().schedule(() -> start(host, port), 20, TimeUnit.SECONDS);
//            }
//        });
    }

}
