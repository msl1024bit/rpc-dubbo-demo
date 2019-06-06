package com.liujq.demo.rpc.protocol.http;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http服务端
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class HttpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    /**
     * 启动Tomcat
     *
     * @param hostName 域名
     * @param port 端口
     */
    public void start(String hostName, Integer port) {

        try {
            Tomcat tomcat = new Tomcat();
            Server server = tomcat.getServer();
            Service service = server.findService("Tomcat");

            Connector connector = new Connector();
            connector.setPort(port);

            Engine engine = new StandardEngine();
            engine.setDefaultHost(hostName);

            Host host = new StandardHost();
            host.setName(hostName);

            String contextPath = "";
            Context context = new StandardContext();
            context.setPath(contextPath);
            context.addLifecycleListener(new Tomcat.FixContextListener());

            host.addChild(context);
            engine.addChild(host);

            service.setContainer(engine);
            service.addConnector(connector);

            tomcat.addServlet(contextPath, "dispatcher", new DispatchServlet());
            context.addServletMappingDecoded("/*", "dispatcher");

            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception e) {
            LOGGER.error("http server start error", e);
        }

    }
}
