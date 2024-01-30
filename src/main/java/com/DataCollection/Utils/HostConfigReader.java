package com.DataCollection.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Properties;

public class HostConfigReader {
    private Properties properties;

    public HostConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int findFirstAvailablePort() {
        for (int port = 8082; port <= 65535; port++) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                return port; // the port is available
            } catch (IOException ex) {
                // the port is not available, continue to the next port
            }
        }
        throw new IllegalStateException("Could not find an available port between 8082 and 65535");
    }

    public String getLocalHostname(){
        return properties.getProperty("local.hostname");
    }

    public String getRemoteVM(){
        return properties.getProperty("remoteVM.hostname");
    }

    public String getDockerImageName(){
        return properties.getProperty("image.name");
    }

    public String getDockerContainerName(){
        return properties.getProperty("record.name");
    }

    public int getDockerContainerPort(){
        return Integer.parseInt(properties.getProperty("record.port"));
    }

    public int getDockerHostPort(){
        return Integer.parseInt(properties.getProperty("host.port"));
    }

    public String getTestUrl(){
        return properties.getProperty("test_url");
    }
}
