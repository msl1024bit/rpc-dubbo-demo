package com.liujq.demo.rpc.framework;

import com.liujq.demo.rpc.protocol.dubbo.DubboProtocol;
import com.liujq.demo.rpc.protocol.http.HttpProtocol;

/**
 * 通信协议工厂
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class ProtocolFactory {

    /**
     * 获取具体的通信协议类
     *
     * @return 通信协议类
     */
    public static Protocol getProtocol() {

        // 获取系统配置参数protocolName
        String protocolName = System.getProperty("protocolName");
        if (protocolName == null || "".equals(protocolName)) {
            return new DubboProtocol();
        }
        switch (protocolName) {
            case "http":
                return new HttpProtocol();
            case "dubbo":
                return new DubboProtocol();
            default:
                break;
        }
        return new DubboProtocol();
    }
}
