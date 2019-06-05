package com.liujq.demo.rpc.protocol.http;

import com.liujq.demo.rpc.framework.Request;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * http客户端
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class HttpClient {

    /**
     * 发送请求
     *
     * @param hostName 服务域名
     * @param port 端口
     * @param request 请求
     * @return 返回数据
     */
    public Object post(String hostName, Integer port, Request request) {

        ObjectOutputStream oos = null;
        try {
            URL url = new URL("http", hostName, port, "/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            // 发送请求数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            oos = new ObjectOutputStream(outputStream);
            oos.writeObject(request);
            oos.flush();

            // 得到响应数据
            InputStream inputStream = httpURLConnection.getInputStream();
            return IOUtils.toString(inputStream, Charset.defaultCharset());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
