package com.genecrusher.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    static Properties properties = new Properties();

    static {
        try {
            properties.load(ClassLoader.getSystemResource("dbconfig.properties").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getDbPropertiesObject() {
        return properties;
    }

}
