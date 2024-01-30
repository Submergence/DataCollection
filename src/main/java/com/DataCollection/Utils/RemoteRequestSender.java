package com.DataCollection.Utils;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteRequestSender {
    public JSONObject sendRequest(String remoteUrl, JSONObject requestBody) throws Exception {
        JSONObject jsonResponse = null;
        try {
            // 设置请求的URL
            URL url = new URL("http://" + remoteUrl + ":8000");

            // 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为POST
            connection.setRequestMethod("POST");

            // 允许输入和输出流
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // 设置请求体参数（如果有的话）
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(requestBody.toString());
            outputStream.flush();
            outputStream.close();

            // 获取响应状态码
            int responseCode = connection.getResponseCode();

            // 如果请求成功，读取响应内容
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 解析响应体为JSONObject
                jsonResponse = new JSONObject(response.toString());
            } else {
                // 请求失败，打印错误消息
                System.out.println("请求失败，错误码：" + responseCode);
            }

            // 关闭连接
            connection.disconnect();
        } catch (Exception e) {
            return null;
        }

        // 返回响应的JSONObject
        return jsonResponse;
    }
}
