package com.liujq.demo.rpc.framework;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 远程调用请求类
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class Request implements Serializable {

    private static final long serialVersionUID = -2325882160803929453L;

    private static final AtomicLong INVOKE_ID = new AtomicLong(0);

    /**
     * 调用唯一id
     */
    private Long id;

    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数列表
     */
    private Object[] params;

    /**
     * 参数类型
     */
    private Class[] paramTypes;

    public Request() {
        id = newId();
    }

    private long newId() {
        return INVOKE_ID.getAndIncrement();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }
}
