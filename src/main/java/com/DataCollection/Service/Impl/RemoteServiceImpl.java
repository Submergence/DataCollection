package com.DataCollection.Service.Impl;

import com.DataCollection.Service.RemoteService;
import com.DataCollection.Utils.RemoteRequestSender;
import org.json.JSONObject;

public class RemoteServiceImpl implements RemoteService {
    final static String REMOTE_URL = "123.56.192.227";
    final static String CREATE_ACTION = "create_vnc";
    final static String STOP_ACTION = "stop_vnc";
    @Override
    public int createVncRequest(String records_id, String testUrl) throws Exception {
        RemoteRequestSender sender = new RemoteRequestSender();
        JSONObject requestBody = new JSONObject();
        requestBody.put("action", CREATE_ACTION);
        requestBody.put("records_id", records_id);
        requestBody.put("url", testUrl);
        JSONObject response;
        response = sender.sendRequest(REMOTE_URL, requestBody);

        if (response.has("vncserver_number")) {
            return response.getInt("vncserver_number");
        } else {
            return 0;
        }
    }

    @Override
    public String stopVncRequest(int vncId, String records_id) {
        RemoteRequestSender sender = new RemoteRequestSender();
        JSONObject requestBody = new JSONObject();
        requestBody.put("action", STOP_ACTION);
        requestBody.put("vnc_id", vncId);
        requestBody.put("records_id", records_id);
        JSONObject response = null;
        try {
            response = sender.sendRequest(REMOTE_URL, requestBody);
        }catch (Exception e){
            e.printStackTrace();
            return response.getString("message");
        }
        if(response.has("file")) {
            return response.getString("file");
        } else {
            return null;
        }
    }
}
