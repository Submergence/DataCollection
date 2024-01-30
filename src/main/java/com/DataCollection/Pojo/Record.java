package com.DataCollection.Pojo;

public class Record {

    private String imageName;

    private String containerName;

    private int hostPort;

    private int containerPort;

    private String containerId;

    private int id;

    private int vnc_port;

    private String record;

    private long ctime;

    private String testUrl;

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTestUrl() {
        return testUrl;
    }

    public void setTestUrl(String testUrl) {
        this.testUrl = testUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVnc_port() {
        return vnc_port;
    }

    public void setVnc_port(int vnc_port) {
        this.vnc_port = vnc_port;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "Record{" +
                "imageName='" + imageName + '\'' +
                ", containerName='" + containerName + '\'' +
                ", hostPort=" + hostPort +
                ", containerPort=" + containerPort +
                ", containerId='" + containerId + '\'' +
                ", id=" + id +
                ", vnc_port=" + vnc_port +
                ", record='" + record + '\'' +
                ", ctime=" + ctime +
                ", testUrl='" + testUrl + '\'' +
                ", userId=" + userId +
                '}';
    }

    public Record(String imageName, String containerName, int hostPort, int containerPort, long ctime, String testUrl, int userId) {
        this.imageName = imageName;
        this.containerName = containerName;
        this.hostPort = hostPort;
        this.containerPort = containerPort;
        this.ctime = ctime;
        this.testUrl = testUrl;
        this.userId = userId;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public int getHostPort() {
        return hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    public int getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(int containerPort) {
        this.containerPort = containerPort;
    }
}
