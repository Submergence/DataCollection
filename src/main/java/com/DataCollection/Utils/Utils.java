package com.DataCollection.Utils;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Utils {
    public String getPublicIpAddress(){
        HttpGet request = new HttpGet("http://checkip.amazonaws.com");
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            String result = EntityUtils.toString(response.getEntity()).trim();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

}
