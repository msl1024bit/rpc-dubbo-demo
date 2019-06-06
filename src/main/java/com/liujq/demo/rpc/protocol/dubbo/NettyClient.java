package com.liujq.demo.rpc.protocol.dubbo;

import com.liujq.demo.rpc.framework.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * netty客户端
 *
 * @author Jiqiang.Liu
 * @date 2019-05-28
 */
public class NettyClient {

    private static Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    private NettyClientHandler nettyClientHandler = new NettyClientHandler();

    /**
     * 启动netty客户端
     *
     * @param host 域名
     * @param port 端口
     */
    public Object send(String host, int port, Request request) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
                                    .addLast(new ObjectEncoder())
                                    .addLast(nettyClientHandler);
                        }
                    });
            Channel channel = bootstrap.connect().sync().channel();

            // 构建DefaultFuture类
            DefaultFuture defaultFuture = new DefaultFuture(request.getId(), request, channel, null);
            // 远程调用服务提供者
            return defaultFuture.send();

        } catch (Exception e) {
            LOGGER.error("netty client start error", e);
        } finally {
            group.shutdownGracefully();
        }
        return null;
    }
}
