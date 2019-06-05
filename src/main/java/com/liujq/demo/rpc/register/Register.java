package com.liujq.demo.rpc.register;

import com.liujq.demo.rpc.framework.URL;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 注册中心
 *
 * @author Jiqiang.Liu
 * @date 2019-06-05
 */
public class Register {

    private static Map<String, Map<URL, Class>> REGISTER = new HashMap<>();

    public static void register(String interfaceName, URL url, Class implClass) {
        Map<URL, Class> map = new HashMap<>(4);
        map.put(url, implClass);
        REGISTER.put(interfaceName, map);
        // 持久化元数据配置信息
        saveFile();
    }

    /**
     * 持久化元数据配置信息
     */
    private static void saveFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("metaData.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(REGISTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从磁盘上读取元数据配置文件
     *
     * @return 注册信息
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Map<URL, Class>> getFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("metaData.txt");
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            return (Map<String, Map<URL, Class>>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 负载均衡算法
     *
     * @param interfaceName 接口名
     * @return 实现类的服务地址
     */
    public static URL random(String interfaceName) {
        REGISTER = getFile();
        if (Objects.isNull(REGISTER)) {
            return null;
        }
        List<URL> list = new ArrayList<>(REGISTER.get(interfaceName).keySet());
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    /**
     * 获取实现类
     *
     * @param url 服务地址
     * @param interfaceName 接口名称
     * @return 实现类
     */
    public static Class get(URL url, String interfaceName) {
        REGISTER = getFile();
        assert REGISTER != null;
        return REGISTER.get(interfaceName).get(url);
    }
}
