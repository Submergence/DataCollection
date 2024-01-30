package com.DataCollection.Service.Impl;

import com.DataCollection.Dao.ContainerDao;
import com.DataCollection.Dao.Impl.ContainerDaoImpl;
import com.DataCollection.Dao.Impl.GetDataUtil;
import com.DataCollection.Pojo.Record;
import com.DataCollection.Service.BuildContainers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.DataCollection.Service.RemoteService;

public class BuildContainersImpl implements BuildContainers {
    private final ContainerDao containerDao = new ContainerDaoImpl();
//    @Override
//    public void instantiateDockerContainer(Container container, String remoteHostname) {
//
//        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
//
//        CreateContainerResponse containerResponse = dockerClient.createContainerCmd(container.getImageName())
//                .withName(container.getContainerName())
//                .withHostConfig(HostConfig.newHostConfig().withPortBindings(Collections.singletonList(PortBinding.parse(String.format("%d:%d", container.getHostPort(), container.getContainerPort())))))
//                .withExposedPorts(ExposedPort.tcp(container.getContainerPort()))
//                .withCmd("/bin/bash", "/opt/guacamole_client_shell.sh", " ", remoteHostname)
//
//                .exec();
//
//        dockerClient.startContainerCmd(containerResponse.getId()).exec();
//        return;
//    }
    public int initRecords(Record record){
        return containerDao.insertRecord(record);
    }
    public int startVncServer(String records_id, String testUrl) throws Exception{
        RemoteService remoteService = new RemoteServiceImpl();
        return remoteService.createVncRequest(records_id, testUrl);
    }

    public int computeVncPort(int vncServer){
        return vncServer + 5900;
    }

    public String instantiateDockerContainer(Record record, int vncServerPort) {
        try {
            String portBindings = String.format("%d:%d", record.getHostPort(), record.getContainerPort());
            String command = String.format("docker run -d -p %s -e DOCKER_ID=%s -e VM_PORT=%d --name %s %s /bin/bash /opt/guacamole_client_shell.sh",
                    portBindings, record.getContainerName(), vncServerPort, record.getContainerName(), record.getImageName());
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            Process process = processBuilder.start();
            process.waitFor();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String fullId = reader.readLine();
                String containerId =  fullId.substring(0, 12);

                command = String.format("docker exec %s bash -c 'echo DOCKER_ID=%s > /etc/environment'", containerId, containerId);
                processBuilder = new ProcessBuilder(command.split(" "));
                process = processBuilder.start();
                process.waitFor();

                return containerId;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int updateContainerInfo(Record record){
        return containerDao.updateContainerInfo(record);
    }

    public int updateStatus(String recordId, int status){
        return containerDao.updateStatus(recordId, status);
    }

    public int updateStatusById(int id, int status){
        return containerDao.updateStatusById(id, status);
    }

    @Override
    public String[] queryContainersIdByRecordId(int id, int status) {
        GetDataUtil getDataUtil = new GetDataUtil();
        List<String> containerList = getDataUtil.getContainerIdByRecordId(id,status);
        return containerList.toArray(new String[0]);
    }



    public String queryContainerByRecordId(int id) {
        return containerDao.queryContainerByRecordId(id);
    }

    public int queryVncIdByRecordId(int id){
//        return containerDao.queryVncIdByRecordId(id) - 5900;
        return new GetDataUtil().getVncPortByRecordId(id) - 5900;
    }

    public int updateRecordById(int id, String record){
        return containerDao.updateRecordById(id, record);
    }

    @Override
    public int shutdownContainerByProcess(String containerId) {
        int exitCode = -1;
        try {
            // Stop the container
            Process stopProcess = new ProcessBuilder()
                    .command("docker", "stop", containerId)
                    .inheritIO()
                    .start();
            stopProcess.waitFor();

            // Remove the container
            Process rmProcess = new ProcessBuilder()
                    .command("docker", "rm", containerId)
                    .inheritIO()
                    .start();
            exitCode = rmProcess.waitFor();
            return exitCode;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
