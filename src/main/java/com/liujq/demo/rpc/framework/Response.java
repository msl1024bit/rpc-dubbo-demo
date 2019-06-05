package com.liujq.demo.rpc.framework;

import java.io.Serializable;

/**
 * 远程调用响应
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 3934271312120548490L;

    /**
     * 调用唯一id
     */
    private Long id;

    /**
     * 返回数据
     */
    private T data;

    public Response(Long id, T data) {
        this.id = id;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
