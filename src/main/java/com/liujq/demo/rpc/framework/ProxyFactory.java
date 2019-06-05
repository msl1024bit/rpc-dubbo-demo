package com.liujq.demo.rpc.framework;

import com.liujq.demo.rpc.provider.api.HelloService;
import com.liujq.demo.rpc.register.Register;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class ProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class interfaceClass) {
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Request request = new Request();
                request.setInterfaceName(HelloService.class.getName());
                request.setMethodName("sayHello");
                request.setParams(new Object[]{"123123"});
                request.setParamTypes(new Class[]{String.class});

                URL url = Register.random(interfaceClass.getName());

                Protocol protocol = ProtocolFactory.getProtocol();
                return protocol.send(url, request);
            }
        });
    }
}
