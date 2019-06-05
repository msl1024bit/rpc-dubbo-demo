package com.liujq.demo.rpc.protocol.http;

import com.liujq.demo.rpc.framework.Request;
import com.liujq.demo.rpc.register.Register;
import com.liujq.demo.rpc.framework.URL;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * Http请求处理类
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class HttpServerHandler {

    public void handle(HttpServletRequest req, HttpServletResponse resp) {

        try {

            // 获取请求数据
            InputStream inputStream = req.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            Request request = (Request) ois.readObject();

            String interfaceName = request.getInterfaceName();
            URL url = new URL("localhost", 8080);

            // 注册中心获取接口对应的实现类
            Class implClass = Register.get(url, interfaceName);

            // 执行实现类
            Method method = implClass.getMethod(request.getMethodName(), request.getParamTypes());
            String result = (String) method.invoke(implClass.newInstance(), request.getParams());

            OutputStream outputStream = resp.getOutputStream();
            IOUtils.write("Tomcat:" + result, outputStream, Charset.defaultCharset());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
