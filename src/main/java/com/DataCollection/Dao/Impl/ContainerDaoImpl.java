package com.DataCollection.Dao.Impl;

import com.DataCollection.Dao.ContainerDao;
import com.DataCollection.Pojo.Record;

import java.util.List;

public class ContainerDaoImpl extends BaseDao implements ContainerDao {

    @Override
    public int insertRecord(Record record) {
        String sql = "INSERT INTO records (user_id, port, status, ctime, test_url) VALUES (?, ?, ?, ?, ?)";
        return updateAndGetId(sql, record.getUserId(), record.getHostPort(), 0, record.getCtime(), record.getTestUrl());
    }

    @Override
    public int updateContainerInfo(Record record){
        String sql = "UPDATE records SET container_id=?, vnc_port=? WHERE id=?";
        return update(sql, record.getContainerId(), record.getVnc_port(), record.getId());
    }

    @Override
    public int updateStatusById(int id, int status) {
        String sql = "UPDATE records SET status=? WHERE id=?";
        return update(sql, status, id);
    }

    @Override
    public String queryContainerByRecordId(int id) {
        String sql = "SELECT container_id FROM records WHERE id=?";
        return QueryForOne(String.class, sql, String.valueOf(id));
    }

    @Override
    public int queryVncIdByRecordId(int id) {
        String sql = "SELECT vnc_port FROM records WHERE id=?";
        return QueryForOne(Integer.class, sql, id);
    }

    @Override
    public int updateRecordById(int id, String record) {
        String sql = "UPDATE records SET record=? WHERE id=?";
        return update(sql, record,id);
    }

    @Override
    public void queryByCtime() {

    }

    @Override
    public int updateStatus(String recordId, int status) {
        String sql = "UPDATE records SET status=? WHERE id=?";
        return update(sql, status, recordId);
    }

    @Override
    public List<String> queryContainers(int userId) {
        String sql = "SELECT id FROM records WHERE user_id=? and status=1";
        return QueryForList(String.class, sql, userId);
    }



}
