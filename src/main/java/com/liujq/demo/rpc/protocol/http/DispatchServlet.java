package com.liujq.demo.rpc.protocol.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * http请求转发处理类
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class DispatchServlet extends HttpServlet {

    private static final long serialVersionUID = -3080717776832860357L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new HttpServerHandler().handle(req, resp);
    }
}
