package com.DataCollection.Service;

public interface RemoteService {
    /*
    return: vnc id
    */
    public int createVncRequest(String dockerId, String testUrl) throws Exception;

    /*
    return: file
    */
    public String stopVncRequest(int vncId, String record_id);
}
