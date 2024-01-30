package com.DataCollection.Service;

import com.DataCollection.Pojo.Record;

public interface BuildContainers {

    String instantiateDockerContainer(Record record, int vncServerPort);

    int updateStatus(String userId, int status);

//    String[] queryContainersIdByUserId(int userId);

    String[] queryContainersIdByRecordId(int id, int status);

    int shutdownContainerByProcess(String containerId);
}
