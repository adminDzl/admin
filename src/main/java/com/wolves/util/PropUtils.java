package com.wolves.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * 读取properties文件配置
 */
public class PropUtils {


    private static String ENV_FILENAME = "dbconfig.properties";

    private static Properties properties;

    static {
        try {
            loadMessage();
        } catch (IOException e) {
            throw new IllegalStateException("load message resource error", e);
        }
    }

    public static String getConfigMessage(String name, Object... args) {
        String message = properties.getProperty(name);
        if (args == null || args.length == 0) {
            return message;
        }
        return MessageFormat.format(message, args);
    }

    private static void loadMessage() throws IOException {
        if (properties != null) {
            return;
        }
        properties = new Properties();

        properties.load(new BufferedReader(new InputStreamReader(getEnvInputStream(), "utf-8")));
    }

    private static InputStream getEnvInputStream() {
        // 从当前类加载器中加载资源
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(ENV_FILENAME);
        if (is != null) {
            return is;
        }
        // 从系统类加载器中加载资源
        is = ClassLoader.getSystemResourceAsStream(ENV_FILENAME);
        if (is != null) {
            return is;
        }
        throw new IllegalStateException("cannot find " + ENV_FILENAME + " anywhere");
    }

}
