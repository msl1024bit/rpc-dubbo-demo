package com.liujq.demo.rpc.framework;

/**
 * 通信协议接口
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public interface Protocol {

    /**
     * 启动服务端
     *
     * @param url 实现类域名信息
     */
    void start(URL url);

    /**
     * 客户端发送请求
     *
     * @param url 实现类域名信息
     * @param request 请求
     * @return 返回结果
     */
    Object send(URL url, Request request);
}
