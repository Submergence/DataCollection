package com.DataCollection.Dao.Impl;

import com.DataCollection.Utils.MysqlConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



public class GetDataUtil {

    public List<String> getContainerIdByRecordId(int id, int status){
        MysqlConfigReader mysqlConfigReader = new MysqlConfigReader();
        String url = mysqlConfigReader.getUrl();
        String user = mysqlConfigReader.getUsername();
        String password = mysqlConfigReader.getPwd();
        List<String> containerId = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT container_id FROM records WHERE id=? AND status=?";
            try(PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setInt(2, status);
                try(ResultSet rs = stmt.executeQuery()) {
                    while(rs.next()){
                        containerId.add(rs.getString("container_id"));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return containerId;
    }
    public Integer getVncPortByRecordId(int id){
        MysqlConfigReader mysqlConfigReader = new MysqlConfigReader();
        String url = mysqlConfigReader.getUrl();
        String user = mysqlConfigReader.getUsername();
        String password = mysqlConfigReader.getPwd();
        int vncPort = 0;
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT vnc_port FROM records WHERE id=?";
            try(PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try(ResultSet rs = stmt.executeQuery()) {
                    if(rs.next()) { // 确保结果集中有数据
                        vncPort = rs.getInt("vnc_port");
                        if(rs.wasNull()) { // 如果返回的是 SQL NULL，设置 vncPort 为 null
                            vncPort = 5900;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return vncPort;
    }
}