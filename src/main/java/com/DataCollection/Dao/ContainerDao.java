package com.DataCollection.Dao;

import com.DataCollection.Pojo.Record;

import java.util.List;

public interface ContainerDao {

    int insertRecord(Record record);

    public void queryByCtime();

    int updateStatus(String recordId, int status);

    List<String> queryContainers(int userId);

    int updateContainerInfo(Record record);

    int updateStatusById(int id, int status);

    String queryContainerByRecordId(int id);

    int queryVncIdByRecordId(int id);

    int updateRecordById(int id, String record);
}
