package com.liujq.demo.rpc.protocol.http;

import com.liujq.demo.rpc.framework.Request;
import com.liujq.demo.rpc.framework.Protocol;
import com.liujq.demo.rpc.framework.URL;

/**
 * http通信协议 基于tomcat实现
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class HttpProtocol implements Protocol {
    @Override
    public void start(URL url) {
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostName(), url.getPort());
    }

    @Override
    public Object send(URL url, Request request) {
        HttpClient httpClient = new HttpClient();
        return httpClient.post(url.getHostName(), url.getPort(), request);
    }
}
