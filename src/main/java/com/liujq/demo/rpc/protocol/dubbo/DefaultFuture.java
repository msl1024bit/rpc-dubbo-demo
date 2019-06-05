package com.liujq.demo.rpc.protocol.dubbo;

import com.liujq.demo.rpc.framework.Request;
import com.liujq.demo.rpc.framework.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义实现的future类, 用来发送和接受数据
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class DefaultFuture extends CompletableFuture<Object> {

    private static final Map<Long, DefaultFuture> FUTURES = new ConcurrentHashMap<>();

    /**
     * 唯一请求id
     */
    private Long id;

    /**
     * 远程调用请求类
     */
    private Request request;

    /**
     * 通道channel
     */
    private Channel channel;

    /**
     * 响应
     */
    private Response response;

    /**
     * 接口调用超时时间
     */
    private Long timeout = 300L;

    /**
     * 调用开始时间
     */
    private final long start = System.currentTimeMillis();

    public DefaultFuture(Long id, Request request, Channel channel, Response response) {
        this.id = id;
        this.request = request;
        this.channel = channel;
        this.response = response;
        FUTURES.put(id, this);
    }

    /**
     * 根据id获取对应的future
     *
     * @param id 请求唯一标识
     * @return DefaultFuture
     */
    public static DefaultFuture getFuture(long id) {
        return FUTURES.get(id);
    }

    /**
     * 发送消息
     * 根据设置的超时时间 不断轮询DefaultFuture是否已有数据返回
     *
     * @return 返回结果
     */
    public Object send() {
        ChannelFuture channelFuture = channel.writeAndFlush(request);
        try {
            channelFuture.await();
            while (System.currentTimeMillis() - start <= timeout) {
                DefaultFuture defaultFuture = FUTURES.get(request.getId());
                Response response = defaultFuture.getResponse();
                if (Objects.nonNull(response)) {
                    FUTURES.remove(request.getId());
                    return response.getData();
                }
            }
            FUTURES.remove(request.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写入来自server端的返回的响应
     *
     * @param response 响应
     */
    public static void writeResponse(Response response) {
        if (Objects.nonNull(response)) {
            DefaultFuture defaultFuture = FUTURES.get(response.getId());
            defaultFuture.setResponse(response);
            FUTURES.replace(response.getId(), defaultFuture);
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}
