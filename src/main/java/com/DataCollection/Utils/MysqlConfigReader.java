package com.DataCollection.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MysqlConfigReader {
    private Properties properties;

    public MysqlConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("Jdbc.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTable(){
        return properties.getProperty("table");
    }

    public String getUrl(){
        return properties.getProperty("url");
    }

    public String getUsername(){
        return properties.getProperty("username");
    }

    public String getPwd(){
        return properties.getProperty("password");
    }

}
