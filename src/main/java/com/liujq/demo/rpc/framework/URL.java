package com.liujq.demo.rpc.framework;

import java.io.Serializable;
import java.util.Objects;

/**
 * 服务域名信息
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class URL implements Serializable {
    private static final long serialVersionUID = 5791832508540801845L;

    /**
     * 地址域名
     */
    private String hostName;

    /**
     * 端口号
     */
    private Integer port;

    public URL(String hostName, Integer port) {
        this.hostName = hostName;
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URL url = (URL) o;
        return hostName.equals(url.hostName) &&
                port.equals(url.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostName, port);
    }
}
